package sr.app.mylenses.ui.addnewlenses

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sr.app.mylenses.BaseBottomSheetDialog
import sr.app.mylenses.databinding.AddNewLensesBottomSheetFragmentBinding
import sr.app.mylenses.utils.data.repository.RepositoryManager
import sr.app.mylenses.utils.lang.StringsManager
import sr.app.mylenses.utils.notifications.NotificationScheduler
import sr.app.mylenses.utils.preferences.SharedPreferencesManager
import sr.app.mylenses.view.viewpager.AddNewLensesFragmentAdapter
import sr.app.mylenses.view.visible
import kotlin.math.max

class AddNewLensesBottomSheetFragment :
    BaseBottomSheetDialog<AddNewLensesBottomSheetFragmentBinding>(
        AddNewLensesBottomSheetFragmentBinding::inflate
    ) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AddNewLensesFragmentAdapter(this)
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
                    RepositoryManager.lensesRepository.addPair(it)
                    SharedPreferencesManager.stocksSP.takeIf { s -> s > 0 }
                        ?.let { s -> SharedPreferencesManager.stocksSP = max(0, s - 2) }
                    withContext(Dispatchers.Main) {
                        NotificationScheduler.setNotifications(requireContext(), it)
                        dismiss()
                    }
                } ?: dismiss()
            }
        }
    }
}