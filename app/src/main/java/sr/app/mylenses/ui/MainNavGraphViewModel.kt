package sr.app.mylenses.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import sr.app.mylenses.utils.data.repository.RepositoryManager

class MainNavGraphViewModel : ViewModel() {
    val lenses = RepositoryManager.lensesRepository.activeLensPairLiveData
}