package sr.app.mylenses.utils.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import sr.app.mylenses.utils.data.enums.ResourceType
import sr.app.mylenses.utils.data.mapper.Mapper
import sr.app.mylenses.utils.data.model.Resource

@Entity(tableName = "resources")
class ResourceModel(
    @PrimaryKey()
    @ColumnInfo(name = "file_name")
    val fileName: String,
    @ColumnInfo(name = "type")
    val type: ResourceType,
    @ColumnInfo(name = "sync_date")
    val syncDate: DateTime?
) : Mapper<Resource> {
    @ColumnInfo(name = "run_sync")
    var runSync: Boolean = false

    override fun map(): Resource {
        return Resource(fileName, type, syncDate)
    }
}