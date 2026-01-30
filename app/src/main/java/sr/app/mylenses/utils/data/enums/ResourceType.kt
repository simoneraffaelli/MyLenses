package sr.app.mylenses.utils.data.enums

import sr.app.mylenses.utils.data.langAssetPath
import sr.app.mylenses.utils.data.langTypeCode

enum class ResourceType(val assetPath: String, val code: String) {
    Language(langAssetPath, langTypeCode), Undefined("", "");

    companion object {
        fun decode(value: String): ResourceType {
            return entries.singleOrNull { it.code == value } ?: Undefined
        }
    }
}