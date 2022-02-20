package sr.app.mylenses.utils.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import sr.app.mylenses.utils.data.mapper.Mapper
import sr.app.mylenses.utils.data.model.Lens
import sr.app.mylenses.utils.data.utils.Duration
import sr.app.mylenses.utils.data.utils.Type

@Entity(tableName = "lenses")
class LensModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0,
    @ColumnInfo(name = "type")
    val type: Type,
    @ColumnInfo(name = "duration")
    val duration: Duration,
    @ColumnInfo(name = "init_date")
    val initDate: DateTime,
    @ColumnInfo(name = "end_date")
    val endDate: DateTime?,
    @ColumnInfo(name = "is_active")
    val isActive: Boolean
) : Mapper<Lens> {
    override fun map(): Lens {
        return Lens(type, duration, initDate, endDate, isActive)
    }

}
