package sr.app.mylenses.utils.data.enums

import androidx.annotation.Keep
import sr.app.mylenses.utils.lang.StringsManager

@Keep
enum class Duration(val days: Int) {

    weekly(7), biWeekly(14), monthly(31), biMonthly(60), quarterly(120), semiAnnual(180), annual(365), undefined(
        -1
    );

    companion object {
        fun fromInt(value: Int): Duration {
            return values().singleOrNull { it.days == value } ?: undefined
        }

        fun fromIndex(index: Int): Duration {
            runCatching {
                return values()[index]
            }.onFailure {
                return undefined
            }

            return undefined
        }

        val labels: Array<String>
            get() {
                val list = arrayListOf<String>()
                values().forEach {
                    if (it != undefined) {
                        list.add(StringsManager.get(it.name.lowercase()))
                    }
                }
                return list.toTypedArray()
            }
    }
}