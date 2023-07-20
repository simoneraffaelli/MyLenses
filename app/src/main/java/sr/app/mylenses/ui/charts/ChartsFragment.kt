package sr.app.mylenses.ui.charts

import android.animation.ValueAnimator
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import androidx.core.text.bold
import androidx.navigation.navGraphViewModels
import com.google.android.material.transition.MaterialContainerTransform
import org.joda.time.DateTime
import sr.app.mylenses.BaseFragment
import sr.app.mylenses.R
import sr.app.mylenses.databinding.ChartsFragmentBinding
import sr.app.mylenses.ui.MainNavGraphViewModel
import sr.app.mylenses.utils.data.enums.Duration
import sr.app.mylenses.utils.data.model.Lens
import sr.app.mylenses.utils.lang.StringsManager

class ChartsFragment : BaseFragment<ChartsFragmentBinding>(ChartsFragmentBinding::inflate) {

    private val viewModel: MainNavGraphViewModel by navGraphViewModels(R.id.main_nav_graph)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 600
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.barChart.animate(listOf())
        binding.donutChart.animate(listOf())

        viewModel.allLenses.observe(viewLifecycleOwner) { model ->
            val lensesUsed = model.count() * 2
            val lensesUsedWithinDeadline = model.sumOf { lensPair ->
                (1.takeIf {
                    lensPair?.rightLens?.let {
                        (it.endDate ?: DateTime.now()) <= it.expirationDate
                    } ?: false
                } ?: 0).plus(
                    (1.takeIf {
                        lensPair?.leftLens?.let {
                            (it.endDate ?: DateTime.now()) <= it.expirationDate
                        } ?: false
                    } ?: 0)
                )
            }

            val groupedLenses = arrayListOf<Lens>().apply {
                model?.forEach {
                    it?.let {
                        this.add(it.leftLens)
                        this.add(it.rightLens)
                    }
                }
            }.groupBy { it.duration }

            val barSet = listOf(
                StringsManager.get("biweekly") to (groupedLenses[Duration.biWeekly]?.count()
                    ?.toFloat() ?: 0f),
                StringsManager.get("monthly") to (groupedLenses[Duration.monthly]?.count()
                    ?.toFloat() ?: 0f),
                StringsManager.get("bimonthly") to (groupedLenses[Duration.biMonthly]?.count()
                    ?.toFloat()
                    ?: 0f),
                StringsManager.get("quarterly") to (groupedLenses[Duration.quarterly]?.count()
                    ?.toFloat()
                    ?: 0f),
                StringsManager.get("semiannual") to (groupedLenses[Duration.semiAnnual]?.count()
                    ?.toFloat()
                    ?: 0f),
                StringsManager.get("annual") to (groupedLenses[Duration.annual]?.count()?.toFloat()
                    ?: 0f)
            )

            val donutSet = listOf(
                lensesUsedWithinDeadline.toFloat(),
                (lensesUsed - lensesUsedWithinDeadline).toFloat()
            )
            /* Subtitle */
            animateSubtitleTextView(lensesUsed)
            /* DonutChartDesc */
            animateDonutChartDesc(
                lensesUsedWithinDeadline,
                lensesUsed - lensesUsedWithinDeadline
            )
            /* Build Charts */
            buildLastMonthStats(barSet, donutSet)
        }
    }

    private fun buildLastMonthStats(barSet: List<Pair<String, Float>>, donutSet: List<Float>) {
        /**
         * Bar Chart
         */
        binding.barChart.animation.duration = animationDuration
        binding.barChart.animate(barSet)

        /**
         * Donut Chart
         */
        binding.donutChart.donutColors = intArrayOf(
            resources.getColor(R.color.lensesUsedWithinDeadline, requireActivity().theme),
            resources.getColor(R.color.lensesUsedOutOfDeadline, requireActivity().theme)
        )
        binding.donutChart.animation.duration = animationDuration
        binding.donutChart.donutTotal = donutSet.sum()
        binding.donutChart.animate(donutSet)
    }

    private fun animateDonutChartDesc(endValue1: Int, endValue2: Int) {
        val originalStr = StringsManager.get("lensesDeadlineRespected").split('|')
        val animator = ValueAnimator.ofInt(0, intArrayOf(endValue1, endValue2).max())
        animator.duration = animationDuration
        animator.addUpdateListener { animation ->
            binding.donutChartDesc.text = SpannableStringBuilder()
                .append(originalStr[0])
                .bold { append(" ${(animation.animatedValue as Int).coerceAtMost(endValue1)} ") }
                .append(originalStr[1])
                .bold { append(" ${(animation.animatedValue as Int).coerceAtMost(endValue2)} ") }
                .append(originalStr[2])
        }
        animator.start()
    }

    private fun animateSubtitleTextView(endValue: Int) {
        val originalStr = StringsManager.get("lensesUsed").split('|')
        val animator = ValueAnimator.ofInt(0, endValue)
        animator.duration = animationDuration
        animator.addUpdateListener { animation ->
            binding.lineChartDescription.text = SpannableStringBuilder()
                .append(originalStr[0])
                .bold { append(" ${animation.animatedValue} ") }
                .append(originalStr[1])
        }
        animator.start()
    }

    companion object {
        const val animationDuration = 1000L
    }
}