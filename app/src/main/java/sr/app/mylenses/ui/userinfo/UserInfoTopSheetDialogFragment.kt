package sr.app.mylenses.ui.userinfo

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation.findNavController
import sr.app.mylenses.BaseDialogFragment
import sr.app.mylenses.R
import sr.app.mylenses.databinding.UserInfoTopSheetFragmentBinding
import sr.app.mylenses.utils.login.GoogleSSOManager
import sr.app.mylenses.view.gone
import sr.app.mylenses.view.userinfo.UserInfoTopSheetDialog
import sr.app.mylenses.view.visible

class UserInfoTopSheetDialogFragment :
    BaseDialogFragment<UserInfoTopSheetFragmentBinding>(UserInfoTopSheetFragmentBinding::inflate) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return UserInfoTopSheetDialog(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GoogleSSOManager.account.observe(viewLifecycleOwner) { account ->
            account?.let {
                binding.signIn.gone()
                binding.logOutIcon.visible()
                binding.userInfoBox.visible()
                binding.name.text = it.displayName
                binding.email.text = it.email
            }?.let {
                binding.signIn.visible()
                binding.logOutIcon.gone()
                binding.userInfoBox.gone()
            }
        }

        binding.signIn.setOnClickListener {
            googleSignIn()
        }

        binding.logOutIcon.setOnClickListener { googleSignOut() }

        binding.settingsIcon.setOnClickListener {
            findNavController(requireActivity(), R.id.main_activity_nav_host_fragment).navigate(
                UserInfoTopSheetDialogFragmentDirections.actionUserInfoTopSheetDialogFragmentToSettingsFragment()
            )
        }
    }
}