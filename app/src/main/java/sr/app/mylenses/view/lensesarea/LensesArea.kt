package sr.app.mylenses.view.lensesarea

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.res.ResourcesCompat
import com.gelitenight.waveview.library.WaveView
import com.google.android.material.card.MaterialCardView
import sr.app.mylenses.R
import sr.app.mylenses.databinding.LensesAreaLayoutBinding
import sr.app.mylenses.utils.lang.StringsManager
import sr.app.mylenses.view.wave.WaveHelper

class LensesArea @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = R.attr.lensAreaStyle
) : MaterialCardView(context, attributeSet, defStyleAttr) {

    private var _binding: LensesAreaLayoutBinding? = null
    private val binding: LensesAreaLayoutBinding
        get() = _binding!!

    private lateinit var leftLensWaveHelper: WaveHelper
    private lateinit var rightLensWaveHelper: WaveHelper

    init {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        _binding = LensesAreaLayoutBinding.inflate(inflater, this, true)

        initWaves()
    }

    private fun initWaves() {
        binding.leftWave.let {
            leftLensWaveHelper = WaveHelper(it)
            initView(it)
        }

        binding.rightWave.let {
            rightLensWaveHelper = WaveHelper(it)
            initView(it)
        }
    }

    private fun initView(waveView: WaveView) {
        waveView.setShapeType(WaveView.ShapeType.SQUARE)
        waveView.setBorder(0, 0)
        waveView.setWaveColor(
            ResourcesCompat.getColor(
                resources,
                R.color.waveViewSecondaryColor,
                null
            ), ResourcesCompat.getColor(resources, R.color.waveViewPrimaryColor, null)
        )
    }


    fun addLenses(left: LensesAreaItem, right: LensesAreaItem) {
        binding.leftLensCountDown.text = left.timeLeft
        binding.leftLensETA.text = left.expirationDate
        leftLensWaveHelper.changeLevel(left.percentage)
        binding.rightLensCountDown.text = right.timeLeft
        binding.rightLensETA.text = right.expirationDate
        rightLensWaveHelper.changeLevel(right.percentage)
    }

    fun startWaveAnimator() {
        leftLensWaveHelper.start()
        rightLensWaveHelper.start()
    }

    fun cancelWaveAnimator() {
        leftLensWaveHelper.cancel()
        rightLensWaveHelper.cancel()
    }

    fun reset() {
        binding.leftLensCountDown.text = StringsManager.get("singleDashPlaceholder")
        binding.leftLensETA.text = StringsManager.get("tripleDashPlaceholder")
        leftLensWaveHelper.changeLevel(0f)
        binding.rightLensCountDown.text = StringsManager.get("singleDashPlaceholder")
        binding.rightLensETA.text = StringsManager.get("tripleDashPlaceholder")
        rightLensWaveHelper.changeLevel(0f)
    }
}