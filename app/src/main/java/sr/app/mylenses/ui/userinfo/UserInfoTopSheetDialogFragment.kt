package sr.app.mylenses.ui.userinfo

import android.app.Dialog
import android.os.Bundle
import android.view.View
import sr.app.mylenses.BaseDialogFragment
import sr.app.mylenses.databinding.UserInfoTopSheetFragmentBinding
import sr.app.mylenses.utils.navigation.safeNavController
import sr.app.mylenses.utils.navigation.safeNavigate
import sr.app.mylenses.view.userinfo.UserInfoTopSheetDialog

class UserInfoTopSheetDialogFragment :
    BaseDialogFragment<UserInfoTopSheetFragmentBinding>(UserInfoTopSheetFragmentBinding::inflate) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return UserInfoTopSheetDialog(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsIcon.setOnClickListener {
            safeNavController?.safeNavigate(
                UserInfoTopSheetDialogFragmentDirections.actionUserInfoTopSheetDialogFragmentToSettingsFragment()
            )
        }
    }
}