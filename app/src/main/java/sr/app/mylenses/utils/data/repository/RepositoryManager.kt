package sr.app.mylenses.utils.data.repository

import sr.app.mylenses.MyLensesApp
import sr.app.mylenses.utils.data.database.AppDatabase

object RepositoryManager {

    private val ctx get() = MyLensesApp.instance

    val lensesRepository: LensesRepository by lazy {
        val db = AppDatabase(ctx)
        LensesRepository(db.lensesDao())
    }

    val resourcesRepository: ResourcesRepository by lazy {
        val db = AppDatabase(ctx)
        ResourcesRepository(db.resourcesDao())
    }
}