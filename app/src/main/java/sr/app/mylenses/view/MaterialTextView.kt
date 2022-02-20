package sr.app.mylenses.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textview.MaterialTextView
import sr.app.mylenses.R
import sr.app.mylenses.utils.lang.StringsManager

class MaterialTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialTextViewStyle
) : MaterialTextView(context, attributeSet, defStyleAttr) {

    init {
        val attrs = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.MaterialTextView,
            defStyleAttr,
            R.style.DefaultTextView
        )
        try {
            val langText = attrs.getString(R.styleable.MaterialTextView_langKey)
            text = if (langText.isNullOrEmpty())
                attrs.getText(R.styleable.MaterialTextView_android_text)
            else
                StringsManager.get(langText)
        } finally {
            attrs.recycle()
        }
    }
}