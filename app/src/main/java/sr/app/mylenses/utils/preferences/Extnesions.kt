package sr.app.mylenses.utils.preferences

import android.content.SharedPreferences

inline fun <reified T> SharedPreferences.liveData(
    key: String,
    default: T
): SharedPreferenceLiveData<T> {
    return SharedPreferenceLiveData(this, key) {
        when (default) {
            is String -> getString(key, default) as T
            is Int -> getInt(key, default) as T
            is Long -> getLong(key, default) as T
            is Boolean -> getBoolean(key, default) as T
            is Float -> getFloat(key, default) as T
            is Set<*> -> getStringSet(key, default as Set<String>) as T
            is MutableSet<*> -> getStringSet(key, default as MutableSet<String>) as T
            else -> throw IllegalArgumentException("Generic type not handled yet.")
        }
    }
}