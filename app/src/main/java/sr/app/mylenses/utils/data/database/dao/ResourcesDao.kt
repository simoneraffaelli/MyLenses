package sr.app.mylenses.utils.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import sr.app.mylenses.utils.data.database.models.ResourceApiModel
import sr.app.mylenses.utils.data.database.models.ResourceModel

@Dao
interface ResourcesDao {
    @Query("SELECT * FROM resources WHERE filename > :filename")
    fun getResource(filename: String): ResourceModel

    @Upsert(ResourceModel::class)
    fun update(model: ResourceApiModel)
}