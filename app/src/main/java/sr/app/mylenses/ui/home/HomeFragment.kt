package sr.app.mylenses.ui.home

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sr.app.mylenses.BaseFragment
import sr.app.mylenses.R
import sr.app.mylenses.databinding.HomeFragmentBinding
import sr.app.mylenses.ui.MainNavGraphViewModel
import sr.app.mylenses.utils.data.repository.RepositoryManager
import sr.app.mylenses.utils.lang.StringsManager
import sr.app.mylenses.utils.lang.curiosities
import sr.app.mylenses.utils.login.GoogleSSOManager
import sr.app.mylenses.utils.preferences.SharedPreferencesManager
import sr.app.mylenses.view.lensesarea.LensesAreaItem

class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    private val viewModel: MainNavGraphViewModel by navGraphViewModels(R.id.main_nav_graph)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.container.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        binding.carouselTextView.textList = curiosities

        binding.addNewLensesButton.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(HomeFragmentDirections.actionHomeFragmentToAddNewLensesBottomSheetFragment())
        }

        binding.shop.setOnClickListener {
            val extras: FragmentNavigator.Extras =
                FragmentNavigator.Extras.Builder().addSharedElement(it, "sharedElem_comingSoon")
                    .build()
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToComingSoonFragment(), extras)
        }

        binding.userIcon.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(HomeFragmentDirections.actionHomeFragmentToUserInfoTopSheetDialogFragment())
        }

        viewModel.activeLenses.observe(viewLifecycleOwner) { pair ->
            pair?.let {
                binding.lensesArea.addLenses(
                    LensesAreaItem(it.leftLens),
                    LensesAreaItem(it.rightLens)
                )

                binding.lensesArea.deleteButtonClickListener = View.OnClickListener {
                    lifecycleScope.launch(Dispatchers.Default) {
                        RepositoryManager.lensesRepository.deactivateActiveLenses()
                    }
                }
            } ?: binding.lensesArea.reset()
        }

        binding.stocks.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStocksBottomSheetFragment())
        }

        binding.history.setOnClickListener {
            val extras: FragmentNavigator.Extras =
                FragmentNavigator.Extras.Builder().addSharedElement(it, "sharedElem_history")
                    .build()
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToChartsFragment(), extras)
        }

        SharedPreferencesManager.stocksSPLiveData.observe(viewLifecycleOwner) {
            binding.stockAdditonalInfo.text =
                StringsManager.get("lensesLeft", it.takeIf { it > 0 } ?: 0)
        }

        GoogleSSOManager.account.observe(viewLifecycleOwner) { acc ->
            if (acc == null) {
                binding.titleBar.text = StringsManager.get("hi")
                binding.userIconImageView.setImageResource(R.drawable.ic_account)
            } else {
                binding.titleBar.text = "${StringsManager.get("hi")}${
                    acc.displayName?.split(' ')
                        ?.get(0)?.let { ", $it" }
                }"
                acc.photoUrl?.let {
                    Glide.with(this).load(it).into(binding.userIconImageView)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.lensesArea.startWaveAnimator()
    }

    override fun onStop() {
        super.onStop()
        binding.lensesArea.cancelWaveAnimator()
    }
}