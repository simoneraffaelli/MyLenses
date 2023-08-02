package sr.app.mylenses.utils.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import org.joda.time.DateTime
import sr.app.mylenses.utils.data.database.dao.LensesDao
import sr.app.mylenses.utils.data.model.Lens
import sr.app.mylenses.utils.data.model.LensPair

class LensesRepository(private val dao: LensesDao) {

    val activeLensPair by lazy {
        dao.getActiveLensPair()?.map()
    }

    val activeLensPairLiveData by lazy {
        dao.getActiveLensPairLiveData().map { it?.map() }
    }

    fun getLensesHistory(lastPeriod: Boolean = true): LiveData<List<Lens>> {
        val now = DateTime.now()
        val dateFilter = if (lastPeriod) now.minusDays(90).millis else 0
        return dao.getLastMonthLenses(dateFilter).map { it.map { it.map() } }
    }

    fun addPair(pair: LensPair) {
        dao.addNewLenses(pair)
    }

    fun editActivePair(pair: LensPair) {
        val dbPair = dao.getActiveLensPair()
        dbPair?.let {
            val leftLens = it.leftLens.apply {
                this.duration = pair.leftLens.duration
                this.initDate = pair.leftLens.initDate
            }

            val rightLens = it.rightLens.apply {
                this.duration = pair.rightLens.duration
                this.initDate = pair.rightLens.initDate
            }

            dao.update(leftLens)
            dao.update(rightLens)
        }

    }

    fun deactivateActiveLenses() {
        dao.setEndDateForActiveLenses(DateTime.now())
        dao.deactivateLensPair()
    }
}