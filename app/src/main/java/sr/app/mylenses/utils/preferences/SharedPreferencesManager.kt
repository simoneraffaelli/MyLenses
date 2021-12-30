package sr.app.mylenses.utils.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import sr.app.mylenses.MyLensesApp

object SharedPreferencesManager {
    private val ctx: Context
        get() = MyLensesApp.instance

    private fun createEncryptedSharedPreferences(): SharedPreferences {
        return EncryptedSharedPreferences.create(
            encryptedSPFileName,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            ctx,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private fun createSharedPreferences(): SharedPreferences {
        return ctx.getSharedPreferences(SPFileName, Context.MODE_PRIVATE)
    }

    private val encryptedSharedPreferences by lazy { createEncryptedSharedPreferences() }
    private val sharedPreferences by lazy { createSharedPreferences() }

    var languageSP: String
        get() = sharedPreferences.getString(languageKey, "") ?: ""
        set(value) = sharedPreferences.edit().putString(languageKey, value).apply()
}