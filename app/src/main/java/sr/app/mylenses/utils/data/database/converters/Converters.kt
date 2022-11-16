package sr.app.mylenses.utils.data.database.converters

import androidx.room.TypeConverter
import org.joda.time.DateTime
import sr.app.mylenses.utils.data.enums.Duration
import sr.app.mylenses.utils.data.enums.ResourceType
import sr.app.mylenses.utils.data.enums.Type

class Converters {
    @TypeConverter
    fun intToDuration(value: Int): Duration {
        return Duration.fromInt(value)
    }

    @TypeConverter
    fun durationToInt(duration: Duration): Int {
        return duration.days
    }

    @TypeConverter
    fun intToType(value: Int): Type {
        return Type.fromInt(value)
    }

    @TypeConverter
    fun typeToInt(type: Type): Int {
        return type.ordinal
    }

    @TypeConverter
    fun longToDate(value: Long): DateTime {
        return DateTime(value)
    }

    @TypeConverter
    fun dateToLong(date: DateTime): Long {
        return date.millis
    }

    @TypeConverter
    fun intToResourceType(value: Int): ResourceType {
        return ResourceType.fromInt(value)
    }

    @TypeConverter
    fun resourceTypeToInt(value: ResourceType): Int {
        return value.ordinal
    }
}