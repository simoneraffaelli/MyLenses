package sr.app.mylenses.ui.charts

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import sr.app.mylenses.BaseFragment
import sr.app.mylenses.R
import sr.app.mylenses.databinding.ChartsFragmentBinding
import sr.app.mylenses.view.MaterialTextView

class ChartsFragment : BaseFragment<ChartsFragmentBinding>(ChartsFragmentBinding::inflate) {

    private val lineSet = linkedMapOf<String, Float>(
        "label1" to 5f,
        "label2" to 4.5f,
        "label3" to 4.7f,
        "label4" to 3.5f,
        "label5" to 3.6f,
        "label6" to 7.5f,
        "label7" to 7.5f,
        "label8" to 10f,
        "label9" to 5f,
        "label10" to 6.5f,
        "label11" to 3f,
        "label12" to 4f
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Build Charts */
        //buildLastMonthStats()
    }
/*
    private fun buildLastMonthStats() {
        binding.lineChart.apply {
            this.animation.duration = animationDuration
            gradientFillColors =
                intArrayOf(
                    ContextCompat.getColor(requireContext(), R.color.waveViewPrimaryColor),
                    Color.TRANSPARENT
                )
            animate(lineSet)
        }
    }
*/
    private fun animateTextView(textView: MaterialTextView) {
        val animator = ValueAnimator.ofInt(0, 600)
        animator.duration = animationDuration
        animator.addUpdateListener { animation ->
            textView.text = animation.animatedValue.toString()
        }
        animator.start()
    }

    companion object {
        const val animationDuration = 300L
    }
}