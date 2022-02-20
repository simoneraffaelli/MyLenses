package sr.app.mylenses

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.StyleRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.viewbinding.ViewBinding
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import sr.app.mylenses.databinding.BaseBottomSheetLayoutBinding
import sr.app.mylenses.utils.Inflate

abstract class BaseBottomSheetDialog<VB : ViewBinding>(
    private val inflate: Inflate<VB>,
    @StyleRes private val style: Int? = null
) : BottomSheetDialogFragment() {

    private var _baseBinding: BaseBottomSheetLayoutBinding? = null
    private val baseBinding get() = _baseBinding!!

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        style?.let { setStyle(ContextThemeWrapper(requireActivity(), it).themeResId, it) }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            (it as? BottomSheetDialog)?.findViewById<View>(R.id.design_bottom_sheet)?.background =
                ColorDrawable(Color.TRANSPARENT)
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _baseBinding = BaseBottomSheetLayoutBinding.inflate(inflater, container, false)
        _binding = inflate.invoke(layoutInflater, baseBinding.card, true)
        return baseBinding.root
    }

    override fun onStart() {
        super.onStart()
        //Force Expanded State
        dialog?.let { d ->
            d.findViewById<FrameLayout>(R.id.design_bottom_sheet)?.let { fl ->
                BottomSheetBehavior.from(fl).state = STATE_EXPANDED

            }
        }
    }
}