package sr.app.mylenses.utils.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.joda.time.DateTime
import sr.app.mylenses.utils.data.database.models.LensModel
import sr.app.mylenses.utils.data.model.LensPair

@Dao
abstract class LensesDao {
    @Query("SELECT * FROM lenses WHERE is_active = 1")
    abstract fun getActiveLensesLiveData(): LiveData<List<LensModel>>

    @Query("SELECT * FROM lenses WHERE is_active = 1")
    abstract fun getActiveLenses(): List<LensModel>

    @Query("SELECT * FROM lenses ORDER BY _id")
    abstract fun getAllLensesLiveData(): LiveData<List<LensModel>>

    @Query("UPDATE lenses SET is_active = 0 WHERE is_active = 1")
    abstract fun deactivateAllLenses()

    @Query("UPDATE lenses SET end_date = :endDate WHERE is_active = 1")
    abstract fun setEndDateForActiveLenses(endDate: DateTime)

    @Insert
    abstract fun insert(lens: LensModel)

    @Update
    abstract fun update(model: LensModel)

    @Transaction
    open fun addNewLenses(pair: LensPair) {
        setEndDateForActiveLenses(DateTime.now())
        deactivateAllLenses()
        pair.let {
            insert(it.leftLens.map())
            insert(it.rightLens.map())
        }
    }
}