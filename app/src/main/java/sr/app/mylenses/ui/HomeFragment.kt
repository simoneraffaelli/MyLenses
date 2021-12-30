package sr.app.mylenses.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import sr.app.mylenses.BaseFragment
import sr.app.mylenses.databinding.HomeFragmentBinding
import sr.app.mylenses.utils.log.d
import sr.app.mylenses.utils.login.GoogleSSOManager

class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GoogleSSOManager.account.observe(viewLifecycleOwner, { acc ->
            acc?.let {
                d("Name: ${it.displayName}, Email: ${it.email}, Photo Url: ${it.photoUrl}")
            }
        })
    }
}