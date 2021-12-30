package sr.app.mylenses.utils.lang

import sr.app.mylenses.utils.preferences.SharedPreferencesManager
import java.util.*

internal var currentLocale
    get() =
        SharedPreferencesManager.languageSP.takeIf { it.isNotEmpty() }
            ?.let { Locale.forLanguageTag(it) } ?: Locale.getDefault()
    set(value) {
        SharedPreferencesManager.languageSP = value.language
    }
internal val defaultLanguageLocale = Locale.ENGLISH
internal fun langFileName(localeCode: String) = "lang-$localeCode.json"
internal const val assetPath = "languages/"
internal const val documentsPath = "resources/$assetPath"