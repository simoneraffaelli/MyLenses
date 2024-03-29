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
import sr.app.mylenses.utils.lang.StringsManager
import sr.app.mylenses.view.gone
import sr.app.mylenses.view.visible

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
            if (model.isEmpty()) {
                binding.noDataContainer.visible()
                binding.scrollview.gone()
            } else {
                binding.noDataContainer.gone()
                binding.scrollview.visible()

                val lensesUsed = model.count()
                val lensesUsedWithinDeadline =
                    model.count { (it.endDate ?: DateTime.now()) <= it.expirationDate }
                val groupedLenses = model.groupBy { it.duration }

                val barSet = listOf(
                    getBarChartDescription("weekly") to (groupedLenses[Duration.weekly]?.count()
                        ?.toFloat() ?: 0f),
                    getBarChartDescription("biweekly") to (groupedLenses[Duration.biWeekly]?.count()
                        ?.toFloat() ?: 0f),
                    getBarChartDescription("monthly") to (groupedLenses[Duration.monthly]?.count()
                        ?.toFloat() ?: 0f),
                    getBarChartDescription("bimonthly") to (groupedLenses[Duration.biMonthly]?.count()
                        ?.toFloat()
                        ?: 0f),
                    getBarChartDescription("quarterly") to (groupedLenses[Duration.quarterly]?.count()
                        ?.toFloat()
                        ?: 0f),
                    getBarChartDescription("semiannual") to (groupedLenses[Duration.semiAnnual]?.count()
                        ?.toFloat()
                        ?: 0f),
                    getBarChartDescription("annual") to (groupedLenses[Duration.annual]?.count()
                        ?.toFloat()
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

    private fun getBarChartDescription(key: String): String {
        val str = StringsManager.get(key)
        return str.takeIf { it.length > 10 }?.let { "${it.take(8)}…" } ?: str
    }

    companion object {
        const val animationDuration = 1000L
    }
}