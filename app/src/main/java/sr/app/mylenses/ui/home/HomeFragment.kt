package sr.app.mylenses.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import sr.app.mylenses.BaseFragment
import sr.app.mylenses.R
import sr.app.mylenses.databinding.HomeFragmentBinding
import sr.app.mylenses.ui.MainNavGraphViewModel
import sr.app.mylenses.utils.lang.StringsManager
import sr.app.mylenses.utils.lang.curiosities
import sr.app.mylenses.utils.log.d
import sr.app.mylenses.utils.login.GoogleSSOManager
import sr.app.mylenses.utils.preferences.SharedPreferencesManager
import sr.app.mylenses.view.lensesarea.LensesAreaItem

class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    private val viewModel: MainNavGraphViewModel by navGraphViewModels(R.id.main_nav_graph)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.carouselTextView.textList = curiosities

        binding.addNewLensesButton.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(HomeFragmentDirections.actionHomeFragmentToAddNewLensesBottomSheetFragment())
        }

        binding.shop.setOnClickListener {
            val extras: FragmentNavigator.Extras =
                FragmentNavigator.Extras.Builder().addSharedElement(it, "sharedElem_comingSoon")
                    .build()
            Navigation.findNavController(it)
                .navigate(HomeFragmentDirections.actionHomeFragmentToComingSoonFragment(), extras)
        }

        binding.userIcon.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(HomeFragmentDirections.actionHomeFragmentToUserInfoTopSheetDialogFragment())
        }

        viewModel.lenses.observe(viewLifecycleOwner) { pair ->
            d("Dentro observer livedata")
            pair?.let {
                binding.lensesArea.addLenses(
                    LensesAreaItem(it.leftLens),
                    LensesAreaItem(it.rightLens)
                )
            } ?: binding.lensesArea.reset()
        }

        binding.stocks.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(HomeFragmentDirections.actionHomeFragmentToStocksBottomSheetFragment())
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