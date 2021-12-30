package sr.app.mylenses.utils.lang

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import sr.app.mylenses.MyLensesApp
import sr.app.mylenses.utils.log.w
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.HashMap

/**
 * Object that manages the language keys all over the application.
 *
 * @constructor Create empty Strings manager
 */
object StringsManager {
    private var languageMap: HashMap<String, String> = HashMap()

    fun init(ctx: Context) {
        changeLanguage(currentLocale, ctx)
    }

    /**
     * Function that checks if the application support the language.
     *
     * @param locale
     * @return
     */
    private fun localeSupported(locale: Locale): Boolean {
        val fileName = langFileName(locale.language)
        return MyLensesApp.instance.assets.list(assetPath)
            ?.contains(fileName) == true
    }


    /**
     * Function that changes the current language.
     *
     * @param languageLocale
     * @param ctx
     */
    fun changeLanguage(languageLocale: Locale, ctx: Context) {
        /* Check locale compatibility */
        val locale = if (localeSupported(languageLocale)) {
            languageLocale
        } else {
            w("Locale Not Supported!")
            defaultLanguageLocale
        }
        /* Init New Lang */
        languageMap = loadLanguageMap(locale)
        setApplicationLocale(locale, ctx)

        /* fallback: Add spare lang key not translated yet */
        if (languageLocale.language != defaultLanguageLocale.language) {
            val dfMap = loadLanguageMap(defaultLanguageLocale)
            for ((key, value) in dfMap) {
                if (!languageMap.containsKey(key)) {
                    languageMap[key] = value
                }
            }
        }
    }

    private fun setApplicationLocale(locale: Locale, ctx: Context) {
        ctx.resources.configuration.setLocale(locale)
    }

    /**
     * Main function used to decode the translated sentence / word based on the current language.
     *
     * @param key Language Key.
     * @param args If there are some arguments inside the string.
     * @return The decoded key, in the current language.
     */
    fun get(key: String, vararg args: Any = arrayOf()): String {
        return if (languageMap.containsKey(key)) {
            if (args.isEmpty()) {
                languageMap[key]!!
            } else {
                String.format(languageMap[key]!!, *args)
            }
        } else key
    }

    private fun loadLanguageMap(locale: Locale): HashMap<String, String> {
        val json = JsonParser.parseReader(
            InputStreamReader(
                loadJson(locale.language),
                StandardCharsets.UTF_8
            )
        )
        val typeToken =
            TypeToken.getParameterized(HashMap::class.java, String::class.java, String::class.java)
        return Gson().fromJson(json, typeToken.type)
    }

    private fun loadJson(langCode: String): InputStream {
        val langDocPath = "${MyLensesApp.instance.filesDir}$documentsPath${langFileName(langCode)}"
        val file = File(langDocPath)
        return if (file.exists()) FileInputStream(file) else openLangFromAsset(langCode)
    }

    private fun openLangFromAsset(langCode: String): InputStream {
        val langAssetPath = "$assetPath${langFileName(langCode)}"
        return MyLensesApp.instance.assets.open(langAssetPath)
    }
}