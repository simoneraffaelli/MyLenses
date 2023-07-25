package sr.app.mylenses.utils.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import org.joda.time.DateTime
import sr.app.mylenses.utils.data.database.models.ResourceApiModel
import sr.app.mylenses.utils.data.database.models.ResourceModel

@Dao
interface ResourcesDao {
    @Query("SELECT * FROM resources WHERE sync_date IS NULL OR sync_date < last_updated")
    fun getResourcesToDownload(): List<ResourceModel>

    @Query("SELECT * FROM resources WHERE filename = :filename")
    fun getResource(filename: String): ResourceModel

    @Upsert(ResourceModel::class)
    fun update(model: ResourceApiModel)

    @Query("UPDATE resources SET sync_date = :date WHERE filename = :filename")
    fun updateSyncDate(filename: String, date: DateTime)
}