package sr.app.mylenses.utils.lang

import android.content.Context
import sr.app.mylenses.MyLensesApp
import sr.app.mylenses.utils.data.langAssetPath
import java.util.*


internal val defaultLanguageLocale = Locale.ENGLISH
internal fun langFileName(localeCode: String) = "lang-$localeCode.json"

internal val currentLocale: Locale
    get() {
        return Locale.getDefault().takeIf { it.isSupported(MyLensesApp.instance) }
            ?: defaultLanguageLocale
    }

fun Locale.isSupported(context: Context): Boolean {
    return context.assets.list(langAssetPath)?.contains(langFileName(this.language)) == true
}

val curiosities: Array<String>
    get() {
        return StringsManager.publicMap.filter { it.key.startsWith("cur-") }.values.toTypedArray()
    }