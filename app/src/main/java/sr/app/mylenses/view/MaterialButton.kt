package sr.app.mylenses.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import sr.app.mylenses.R
import sr.app.mylenses.utils.lang.StringsManager

class MaterialButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialButtonStyle
) : MaterialButton(context, attributeSet, defStyleAttr) {

    init {
        val attrs = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.MaterialButton,
            defStyleAttr,
            R.style.DefaultButton
        )
        try {
            val langText = attrs.getString(R.styleable.MaterialButton_langKey)
            text = if (langText.isNullOrEmpty())
                attrs.getText(R.styleable.MaterialButton_android_text)
            else
                StringsManager.get(langText)
        } finally {
            attrs.recycle()
        }
    }
}