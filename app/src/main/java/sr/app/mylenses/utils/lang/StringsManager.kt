package sr.app.mylenses.utils.lang

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import sr.app.mylenses.MyLensesApp
import sr.app.mylenses.utils.data.langAssetPath
import sr.app.mylenses.utils.data.load
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * Object that manages the language keys all over the application.
 *
 * @constructor Create empty Strings manager
 */
object StringsManager {
    private var languageMap: HashMap<String, String> = HashMap()
    val publicMap: Map<String, String>
        get() = languageMap

    init {
        val locale = currentLocale
        languageMap = loadLanguageMap(locale)

        /* fallback: Add spare lang key not translated yet */
        if (locale.language != defaultLanguageLocale.language) {
            val dfMap = loadLanguageMap(defaultLanguageLocale)
            for ((key, value) in dfMap) {
                if (!languageMap.containsKey(key)) {
                    languageMap[key] = value
                }
            }
        }
    }

    fun init() {}

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
                load(MyLensesApp.instance, langAssetPath, langFileName(locale.language)),
                StandardCharsets.UTF_8
            )
        )
        val typeToken =
            TypeToken.getParameterized(HashMap::class.java, String::class.java, String::class.java)
        return Gson().fromJson(json, typeToken.type)
    }
}