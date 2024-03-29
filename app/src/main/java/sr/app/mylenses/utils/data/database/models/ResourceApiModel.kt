package sr.app.mylenses.utils.data.database.models

import androidx.room.ColumnInfo
import com.google.errorprone.annotations.Keep
import org.joda.time.DateTime
import sr.app.mylenses.utils.data.enums.ResourceType

@Keep
data class ResourceApiModel(
    @ColumnInfo(name = "filename")
    val fileName: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "type")
    val type: ResourceType,
    @ColumnInfo(name = "last_updated")
    val lastUpdated: DateTime,
)