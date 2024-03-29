package sr.app.mylenses.utils.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.google.errorprone.annotations.Keep
import sr.app.mylenses.utils.data.database.converters.Converters
import sr.app.mylenses.utils.data.database.dao.LensesDao
import sr.app.mylenses.utils.data.database.dao.ResourcesDao
import sr.app.mylenses.utils.data.database.models.LensModel
import sr.app.mylenses.utils.data.database.models.LensPairModel
import sr.app.mylenses.utils.data.database.models.ResourceModel
import sr.app.mylenses.utils.data.databaseName

@Keep
@Database(
    entities = [LensModel::class, LensPairModel::class, ResourceModel::class],
    version = 100,
    exportSchema = true,
    autoMigrations = []
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lensesDao(): LensesDao
    abstract fun resourcesDao() : ResourcesDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, databaseName
        )
            .fallbackToDestructiveMigration() //TODO: REMOVE BEFORE DEPLOY
            .build()
    }
}