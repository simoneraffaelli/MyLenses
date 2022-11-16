package sr.app.mylenses.utils.data.enums

import sr.app.mylenses.utils.data.langAssetPath

enum class ResourceType(val assetPath: String) {
    Language(langAssetPath), Undefined("");

    companion object {
        fun fromInt(value: Int): ResourceType {
            return values().singleOrNull { it.ordinal == value } ?: Undefined
        }
    }
}