package sr.app.mylenses.utils.data.repository

import sr.app.mylenses.utils.data.database.dao.ResourcesDao
import sr.app.mylenses.utils.data.database.models.ResourceApiModel

class ResourcesRepository(private val dao: ResourcesDao) {

    fun updateFromApi(model: ResourceApiModel) {
        dao.update(model)
    }
}