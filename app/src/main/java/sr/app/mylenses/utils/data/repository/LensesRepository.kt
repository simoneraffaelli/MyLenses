package sr.app.mylenses.utils.data.repository

import androidx.lifecycle.map
import org.joda.time.DateTime
import sr.app.mylenses.utils.data.database.dao.LensesDao
import sr.app.mylenses.utils.data.database.models.LensModel
import sr.app.mylenses.utils.data.model.LensPair
import sr.app.mylenses.utils.data.utils.Type
import sr.app.mylenses.utils.log.w

class LensesRepository(private val dao: LensesDao) {

    val activeLensPair by lazy {
        lensModelToLensPair(dao.getActiveLenses())
    }

    val activeLensPairLiveData by lazy {
        dao.getActiveLensesLiveData().map {
            lensModelToLensPair(it)
        }
    }

    fun addPair(pair: LensPair) {
        dao.addNewLenses(pair)
    }

    fun deactivateActiveLenses() {
        dao.setEndDateForActiveLenses(DateTime.now())
        dao.deactivateAllLenses()
    }

    private fun lensModelToLensPair(model: List<LensModel>): LensPair? {
        return when {
            model.size > 2 -> {
                w("More than 2 active lenses!")
                null
            }
            model.size < 2 -> {
                w("Less than 2 active lenses!")
                null
            }
            else -> {
                val leftLens = model.single { it.type == Type.Left }
                val rightLens = model.single { it.type == Type.Right }
                LensPair(leftLens.map(), rightLens.map())
            }
        }
    }

}