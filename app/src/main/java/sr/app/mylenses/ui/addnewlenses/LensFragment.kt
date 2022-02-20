package sr.app.mylenses.ui.addnewlenses

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.navigation.fragment.navArgs
import org.joda.time.DateTime
import sr.app.mylenses.BaseFragment
import sr.app.mylenses.databinding.LensFragmentBinding
import sr.app.mylenses.utils.data.model.Lens
import sr.app.mylenses.utils.data.utils.Duration
import sr.app.mylenses.utils.data.utils.Type
import sr.app.mylenses.view.dateTime
import java.util.*


class LensFragment : BaseFragment<LensFragmentBinding>(LensFragmentBinding::inflate) {

    private lateinit var lensType: Type

    val lens: Lens
        get() {
            return Lens(
                lensType,
                Duration.fromIndex(binding.spinner.selectedItemPosition),
                binding.datepicker.dateTime
            )
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lensType = navArgs<LensFragmentArgs>().value.lensTypeArgKey
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDatePicker()
        initSpinner()
    }

    private fun initSpinner() {
        binding.spinner.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item,
            Duration.labels
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    private fun initDatePicker() {
        val now = DateTime.now()
        binding.datepicker.apply {
            setFirstVisibleDate(now.year - 1, Calendar.JANUARY, 1)
            setLastVisibleDate(now.year + 2, Calendar.DECEMBER, 31)
            setSelectedDate(now.year, now.monthOfYear - 1, now.dayOfMonth)
        }
    }
}