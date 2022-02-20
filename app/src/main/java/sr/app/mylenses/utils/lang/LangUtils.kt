package sr.app.mylenses.utils.lang

import sr.app.mylenses.MyLensesApp
import java.util.*

internal const val assetPath = "languages/"
internal const val documentsPath = "resources/$assetPath"
internal val defaultLanguageLocale = Locale.ENGLISH
internal fun langFileName(localeCode: String) = "lang-$localeCode.json"

internal val currentLocale: Locale
    get() {
        return Locale.getDefault().takeIf { it.isSupported } ?: defaultLanguageLocale
    }

val Locale.isSupported: Boolean
    get() {
        return MyLensesApp.instance.assets.list(assetPath)
            ?.contains(langFileName(this.language)) == true
    }

val curiosities: Array<String>
    get() {
        return StringsManager.publicMap.filter { it.key.startsWith("cur-") }.values.toTypedArray()
    }