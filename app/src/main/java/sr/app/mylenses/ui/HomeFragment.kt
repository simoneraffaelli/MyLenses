package sr.app.mylenses.ui

import androidx.fragment.app.viewModels
import sr.app.mylenses.BaseFragment
import sr.app.mylenses.databinding.HomeFragmentBinding

class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

}