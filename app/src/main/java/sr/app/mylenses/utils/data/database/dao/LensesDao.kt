package sr.app.mylenses.utils.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.joda.time.DateTime
import sr.app.mylenses.utils.data.database.models.LensModel
import sr.app.mylenses.utils.data.database.models.LensPairEmbeddedModel
import sr.app.mylenses.utils.data.database.models.LensPairModel
import sr.app.mylenses.utils.data.model.LensPair

@Dao
abstract class LensesDao {
    @Transaction
    @Query("SELECT * FROM lens_pair WHERE is_active = 1")
    abstract fun getActiveLensPairLiveData(): LiveData<LensPairEmbeddedModel?>

    @Transaction
    @Query("SELECT * FROM lens_pair WHERE is_active = 1")
    abstract fun getActiveLensPair(): LensPairEmbeddedModel?

    @Query("SELECT * FROM lens WHERE init_date > (:dateMillis - (duration * 86400000)) ORDER BY init_date")
    abstract fun getLastMonthLenses(dateMillis: Long): LiveData<List<LensModel>>

    @Query("UPDATE lens_pair SET is_active = 0 WHERE is_active = 1")
    abstract fun deactivateLensPair()

    @Query("UPDATE lens SET end_date = :endDate WHERE id = (SELECT left_lens_id FROM lens_pair WHERE is_active = 1) OR id = (SELECT right_lens_id FROM lens_pair WHERE is_active = 1) ")
    abstract fun setEndDateForActiveLenses(endDate: DateTime)

    @Insert
    abstract fun insert(lens: LensModel): Long

    @Insert
    abstract fun insertPair(pair: LensPairModel)

    @Update
    abstract fun update(model: LensModel)

    @Transaction
    open fun addNewLenses(pair: LensPair) {
        setEndDateForActiveLenses(DateTime.now())
        deactivateLensPair()
        pair.let {
            val leftId = insert(it.leftLens.map())
            val rightId = insert(it.rightLens.map())
            insertPair(
                LensPairModel(
                    isActive = it.isActive,
                    leftLensId = leftId,
                    rightLensId = rightId
                )
            )
        }
    }
}