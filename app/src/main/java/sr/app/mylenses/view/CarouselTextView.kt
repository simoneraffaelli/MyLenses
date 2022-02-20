package sr.app.mylenses.view

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.DecelerateInterpolator
import com.google.android.material.textview.MaterialTextView
import sr.app.mylenses.R
import sr.app.mylenses.utils.lang.StringsManager
import java.util.*

class CarouselTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = R.attr.carouselTextViewStyle
) : MaterialTextView(context, attributeSet, defStyleAttr) {

    var textList = arrayOf("")
    private val textStack: Stack<CharSequence> = Stack()
    private val recycle: Stack<CharSequence> = Stack()

    private val defaultDuration = 500
    private val defaultDelay = 300000L //5min

    private var _duration: Int? = null
    val duration: Int
        get() = _duration ?: defaultDuration

    private var _delay: Long? = null
    val delay: Long
        get() = _delay ?: defaultDelay

    val randomText: CharSequence
        get() {
            recycle.takeIf { it.empty() }?.addAll(textList)
            textStack.takeIf { it.empty() }?.let {
                while (!recycle.isEmpty()) {
                    textStack.push(recycle.pop())
                }
                textStack.shuffle()
            }
            return textStack.pop().also {
                recycle.push(it)
            }
        }

    private val fadeInAnimation: Animation by lazy {
        AlphaAnimation(0f, 1f).apply {
            interpolator = DecelerateInterpolator()
            duration = 500
            fillAfter = true

            setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    text = randomText
                }

                override fun onAnimationEnd(animation: Animation) {
                    handler.postDelayed({
                        startAnimation(fadeOutAnimation)
                    }, delay)
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }
    }

    private val fadeOutAnimation: Animation by lazy {
        AlphaAnimation(1f, 0f).apply {
            interpolator = AccelerateInterpolator()
            duration = 500
            fillAfter = true

            setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {
                    startAnimation(fadeInAnimation)
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }
    }

    init {
        val attrs = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.CarouselTextView,
            defStyleAttr,
            R.style.DefaultTextView
        )
        try {
            val duration = attrs.getInt(R.styleable.CarouselTextView_duration, -1)
            duration.takeIf { it > 0 }?.let { _duration = it }

            val delay = attrs.getInt(R.styleable.CarouselTextView_delay, -1)
            delay.takeIf { it > 0 }?.let { _duration = it }

            val langModel = attrs.getTextArray(R.styleable.CarouselTextView_langModel)
            langModel?.map { StringsManager.get(it.toString()) }
                ?.let { textList = it.toTypedArray() }

            val model = attrs.getTextArray(R.styleable.CarouselTextView_model)
            takeIf { langModel == null }?.let {
                model?.map { it.toString() }?.toTypedArray()?.let { textList = it }
            }
        } finally {
            attrs.recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimation(fadeInAnimation)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnimations()
    }

    private fun stopAnimations() {
        clearAnimation()
        handler.removeCallbacksAndMessages(null)
        fadeInAnimation.apply {
            cancel()
            reset()
        }
        fadeOutAnimation.apply {
            cancel()
            reset()
        }
    }

}