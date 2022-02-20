package sr.app.mylenses

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import sr.app.mylenses.utils.Inflate
import sr.app.mylenses.utils.log.w
import sr.app.mylenses.utils.login.GoogleSSOManager

abstract class BaseDialogFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) :
    DialogFragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate.invoke(inflater, null, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val getGoogleAccount =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            result.data?.let { GoogleSSOManager.getAccountFromIntent(it) }
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    result.data?.let { GoogleSSOManager.getAccountFromIntent(it) }
                }
                else -> {
                    w("Activity Failed! Result Code: ${result.resultCode}")
                }
            }
        }

    fun googleSignIn() {
        getGoogleAccount.launch(GoogleSSOManager.client.signInIntent)
    }

    fun googleSignOut() {
        GoogleSSOManager.signOut()
    }
}