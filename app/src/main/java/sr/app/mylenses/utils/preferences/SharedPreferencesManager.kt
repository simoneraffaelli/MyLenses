package sr.app.mylenses.utils.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import org.joda.time.DateTime
import sr.app.mylenses.MyLensesApp
import sr.app.mylenses.utils.data.enums.Duration
import androidx.core.content.edit

object SharedPreferencesManager {
    private val ctx: Context
        get() = MyLensesApp.instance

    private fun createSharedPreferences(): SharedPreferences {
        return ctx.getSharedPreferences(SPFileName, Context.MODE_PRIVATE)
    }

    private val sharedPreferences by lazy { createSharedPreferences() }

    var stocksSP: Int
        get() = sharedPreferences.getInt(stocksKey, -1)
        set(value) = sharedPreferences.edit { putInt(stocksKey, value) }

    val stocksSPLiveData: LiveData<Int>
        get() = sharedPreferences.liveData(stocksKey, -1)

    var lastLensesDurationSP: Pair<Duration, Duration>
        get() {
            return Pair(
                Duration.fromInt(sharedPreferences.getInt(lastLeftLensDurationKey, -1)),
                Duration.fromInt(sharedPreferences.getInt(lastRightLensDurationKey, -1))
            )
        }
        set(value) {
            sharedPreferences.edit { putInt(lastLeftLensDurationKey, value.first.days) }
            sharedPreferences.edit { putInt(lastRightLensDurationKey, value.first.days) }
        }

    var lastUpdateCheckSP: DateTime?
        get() = sharedPreferences.getLong(lastUpdateCheckKey, -1).takeIf { it > 0 }
            ?.let { DateTime().withMillis(it) }
        set(value) = sharedPreferences.edit { putLong(lastUpdateCheckKey, value?.millis ?: -1) }
}