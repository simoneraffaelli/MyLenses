package sr.app.mylenses.ui.addnewlenses

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sr.app.mylenses.BaseBottomSheetDialog
import sr.app.mylenses.R
import sr.app.mylenses.databinding.AddNewLensesBottomSheetFragmentBinding
import sr.app.mylenses.ui.MainNavGraphViewModel
import sr.app.mylenses.utils.data.model.LensPair
import sr.app.mylenses.utils.data.repository.RepositoryManager
import sr.app.mylenses.utils.lang.StringsManager
import sr.app.mylenses.utils.notifications.local.NotificationScheduler
import sr.app.mylenses.utils.preferences.SharedPreferencesManager
import sr.app.mylenses.view.gone
import sr.app.mylenses.view.viewpager.AddNewLensesFragmentAdapter
import sr.app.mylenses.view.viewpager.EditLensesFragmentAdapter
import sr.app.mylenses.view.visible
import kotlin.math.max

class EditLensesBottomSheetFragment :
    BaseBottomSheetDialog<AddNewLensesBottomSheetFragmentBinding>(
        AddNewLensesBottomSheetFragmentBinding::inflate
    ) {

    private val viewModel: MainNavGraphViewModel by navGraphViewModels(R.id.main_nav_graph)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visible()
        viewModel.activeLenses.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = EditLensesFragmentAdapter(this, it)
                binding.pager.apply {
                    this.adapter = adapter
                    offscreenPageLimit = 1
                    isUserInputEnabled = false
                    isNestedScrollingEnabled = true
                }
                TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
                    tab.text = when (position) {
                        0 -> StringsManager.get("leftLens")
                        1 -> StringsManager.get("rightLens")
                        else -> null
                    }
                }.attach()
                binding.saveButton.setOnClickListener {
                    binding.progressBar.visible()
                    lifecycleScope.launch(Dispatchers.Default) {
                        adapter.newLenses?.let {
                            editLenses(it)
                        } ?: dismiss()
                    }
                }

                binding.progressBar.gone()
            }
        }
    }

    private suspend fun editLenses(pair: LensPair) {
        RepositoryManager.lensesRepository.editActivePair(pair)
        SharedPreferencesManager.apply {
            /* Set Last Lenses Duration */
            lastLensesDurationSP = Pair(pair.leftLens.duration, pair.rightLens.duration)
        }
        withContext(Dispatchers.Main) {
            NotificationScheduler.setNotifications(requireContext(), pair)
            dismiss()
        }
    }
}