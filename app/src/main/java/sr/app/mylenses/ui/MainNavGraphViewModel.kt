package sr.app.mylenses.ui

import androidx.lifecycle.ViewModel
import sr.app.mylenses.utils.data.repository.RepositoryManager

class MainNavGraphViewModel : ViewModel() {
    val activeLenses = RepositoryManager.lensesRepository.activeLensPairLiveData

    val allLenses = RepositoryManager.lensesRepository.getLensesHistory()
}