package sr.app.mylenses.utils.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import sr.app.mylenses.utils.data.database.converters.Converters
import sr.app.mylenses.utils.data.database.dao.LensesDao
import sr.app.mylenses.utils.data.database.models.LensModel
import sr.app.mylenses.utils.data.databaseName

@Database(
    entities = [LensModel::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lensesDao(): LensesDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var instance: AppDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, databaseName
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}