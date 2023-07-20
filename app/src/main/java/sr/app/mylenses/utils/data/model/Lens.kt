package sr.app.mylenses.utils.data.model

import org.joda.time.DateTime
import org.joda.time.Days
import sr.app.mylenses.utils.data.database.models.LensModel
import sr.app.mylenses.utils.data.mapper.Mapper
import sr.app.mylenses.utils.data.enums.Duration
import sr.app.mylenses.utils.data.enums.Type
import kotlin.math.max

class Lens(
    val type: Type,
    val duration: Duration,
    val initDate: DateTime,
    val endDate: DateTime? = null
) : Mapper<LensModel> {

    val expirationDate: DateTime = initDate.plusDays(duration.days)
    val timeLeft: Int = takeIf { endDate != null }?.let { 0 } ?: max(
        Days.daysBetween(
            DateTime.now(),
            expirationDate.plusDays(1)
        ).days, 0
    )
    val usage: Float = timeLeft.toFloat() / duration.days

    override fun map(): LensModel {
        return LensModel(
            type = type,
            duration = duration,
            initDate = initDate,
            endDate = endDate
        )
    }
}