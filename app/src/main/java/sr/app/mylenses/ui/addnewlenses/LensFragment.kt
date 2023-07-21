package sr.app.mylenses.ui.addnewlenses

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.navigation.fragment.navArgs
import org.joda.time.DateTime
import sr.app.mylenses.BaseFragment
import sr.app.mylenses.databinding.LensFragmentBinding
import sr.app.mylenses.utils.data.enums.Duration
import sr.app.mylenses.utils.data.enums.Type
import sr.app.mylenses.utils.data.model.Lens
import sr.app.mylenses.view.dateTime
import java.util.Calendar


class LensFragment : BaseFragment<LensFragmentBinding>(LensFragmentBinding::inflate) {

    private lateinit var lensType: Type
    private var oldDuration: Duration? = null
    private var oldStartDate: Long = -1

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
        navArgs<LensFragmentArgs>().value.apply {
            lensType = lensTypeArgKey
            oldDuration = lensDurationArgKey
            oldStartDate = lensStartDateArgKey
        }
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
        oldDuration?.takeIf { it != Duration.undefined }?.let {
            runCatching {
                binding.spinner.setSelection(it.ordinal)
            }
        }
    }

    private fun initDatePicker() {
        val date =
            oldStartDate.takeIf { it > 0 }?.let { DateTime().withMillis(it) } ?: DateTime.now()
        binding.datepicker.apply {
            setFirstVisibleDate(date.year - 1, Calendar.JANUARY, 1)
            setLastVisibleDate(date.year + 2, Calendar.DECEMBER, 31)
            setSelectedDate(date.year, date.monthOfYear - 1, date.dayOfMonth)
        }
    }
}