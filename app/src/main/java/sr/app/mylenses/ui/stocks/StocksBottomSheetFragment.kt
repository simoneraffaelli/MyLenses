package sr.app.mylenses.ui.stocks

import android.os.Bundle
import android.view.View
import sr.app.mylenses.BaseBottomSheetDialog
import sr.app.mylenses.databinding.StocksBottomSheetFragmentBinding
import sr.app.mylenses.utils.preferences.SharedPreferencesManager

class StocksBottomSheetFragment :
    BaseBottomSheetDialog<StocksBottomSheetFragmentBinding>(StocksBottomSheetFragmentBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SharedPreferencesManager.stocksSPLiveData.observe(viewLifecycleOwner) {
            binding.stocks.setText(it.takeIf { it > 0 }?.toString() ?: 0.toString())
        }

        binding.reset.setOnClickListener {
            SharedPreferencesManager.stocksSP = 0
            dismiss()
        }

        binding.save.setOnClickListener {
            SharedPreferencesManager.stocksSP = binding.stocks.text.toString().toIntOrNull() ?: 0
            dismiss()
        }
    }
}