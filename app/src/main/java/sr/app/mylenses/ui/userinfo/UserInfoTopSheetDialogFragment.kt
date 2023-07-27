package sr.app.mylenses.ui.userinfo

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation.findNavController
import sr.app.mylenses.BaseDialogFragment
import sr.app.mylenses.R
import sr.app.mylenses.databinding.UserInfoTopSheetFragmentBinding
import sr.app.mylenses.view.userinfo.UserInfoTopSheetDialog

class UserInfoTopSheetDialogFragment :
    BaseDialogFragment<UserInfoTopSheetFragmentBinding>(UserInfoTopSheetFragmentBinding::inflate) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return UserInfoTopSheetDialog(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsIcon.setOnClickListener {
            findNavController(requireActivity(), R.id.main_activity_nav_host_fragment).navigate(
                UserInfoTopSheetDialogFragmentDirections.actionUserInfoTopSheetDialogFragmentToSettingsFragment()
            )
        }
    }
}