package sr.app.mylenses.utils.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.errorprone.annotations.Keep
import org.joda.time.DateTime
import sr.app.mylenses.utils.data.enums.Duration
import sr.app.mylenses.utils.data.enums.Type
import sr.app.mylenses.utils.data.mapper.Mapper
import sr.app.mylenses.utils.data.model.Lens

@Keep
@Entity(tableName = "lens")
class LensModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "type")
    val type: Type,
    @ColumnInfo(name = "duration")
    var duration: Duration,
    @ColumnInfo(name = "init_date")
    var initDate: DateTime,
    @ColumnInfo(name = "end_date")
    val endDate: DateTime?,
) : Mapper<Lens> {
    override fun map(): Lens {
        return Lens(type, duration, initDate, endDate)
    }

}
