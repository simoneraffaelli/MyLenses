package sr.app.mylenses.utils.data.repository

import org.joda.time.DateTime
import sr.app.mylenses.utils.data.database.dao.ResourcesDao
import sr.app.mylenses.utils.data.database.models.ResourceApiModel
import sr.app.mylenses.utils.data.model.Resource

class ResourcesRepository(private val dao: ResourcesDao) {

    val resourcesToDownload: List<Resource>
        get() = dao.getResourcesToDownload().map { it.map() }

    fun updateFromApi(model: ResourceApiModel) {
        dao.update(model)
    }

    fun updateSyncDate(filename: String, date: DateTime) {
        dao.updateSyncDate(filename, date)
    }
}