package sr.app.mylenses.utils

import android.content.Context
import android.text.format.DateFormat.getDateFormat
import android.text.format.DateFormat.getTimeFormat
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat

fun DateTime.formatWithTime(ctx: Context): String {
    val datePattern = (getDateFormat(ctx) as SimpleDateFormat).toPattern()
    val timePattern = (getTimeFormat(ctx) as SimpleDateFormat).toPattern()
    val date = DateTimeFormat.forPattern(datePattern).print(this)
    val time = DateTimeFormat.forPattern(timePattern).print(this)
    return "$time - $date"
}

fun DateTime.format(ctx: Context): String {
    val datePattern = (getDateFormat(ctx) as SimpleDateFormat).toPattern()
    return DateTimeFormat.forPattern(datePattern).print(this)
}