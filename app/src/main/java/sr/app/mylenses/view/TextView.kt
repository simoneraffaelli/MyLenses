package sr.app.mylenses.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import sr.app.mylenses.R
import sr.app.mylenses.utils.lang.StringsManager

class TextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textViewStyle
) : AppCompatTextView(context, attributeSet, defStyleAttr) {

    init {
        val attrs = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.TextView,
            defStyleAttr,
            R.style.DefaultTextView
        )
        try {
            val langText = attrs.getString(R.styleable.TextView_langKey)
            text = if (langText.isNullOrEmpty())
                attrs.getText(R.styleable.TextView_android_text)
            else
                StringsManager.get(langText)
        } finally {
            attrs.recycle()
        }
    }
}