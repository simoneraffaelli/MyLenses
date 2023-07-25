package sr.app.mylenses.utils.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.errorprone.annotations.Keep
import org.joda.time.DateTime
import sr.app.mylenses.utils.data.enums.ResourceType
import sr.app.mylenses.utils.data.mapper.Mapper
import sr.app.mylenses.utils.data.model.Resource

@Keep
@Entity(tableName = "resources")
class ResourceModel(
    @PrimaryKey()
    @ColumnInfo(name = "filename")
    val fileName: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "type")
    val type: ResourceType,
    @ColumnInfo(name = "last_updated")
    val lastUpdated: DateTime,
    @ColumnInfo(name = "sync_date")
    val lastSynchronized: DateTime?,
) : Mapper<Resource> {


    override fun map(): Resource {
        return Resource(fileName, url, type, lastUpdated, lastSynchronized)
    }
}