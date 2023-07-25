package sr.app.mylenses.view.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import sr.app.mylenses.ui.addnewlenses.LensFragment
import sr.app.mylenses.utils.data.enums.Type
import sr.app.mylenses.utils.data.model.LensPair
import sr.app.mylenses.utils.log.w

class EditLensesFragmentAdapter(fragment: Fragment, private val pair: LensPair) :
    FragmentStateAdapter(fragment) {

    private val fragmentList = hashMapOf<Int, LensFragment>()

    val newLenses: LensPair?
        get() {
            return takeIf { this.fragmentList.values.size == itemCount }?.let {
                LensPair(
                    isActive = true,
                    leftLens = this.fragmentList[0]!!.lens,
                    rightLens = this.fragmentList[1]!!.lens
                )
            }
        }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = LensFragment()
        when (position) {
            0 -> fragment.arguments = Bundle().apply {
                putSerializable(lensArgKey, Type.Left)
                putSerializable(lensDurationArgKey, pair.leftLens.duration)
                putSerializable(lensStartDateArgKey, pair.leftLens.initDate.millis)
            }
            1 -> fragment.arguments = Bundle().apply {
                putSerializable(lensArgKey, Type.Right)
                putSerializable(lensDurationArgKey, pair.rightLens.duration)
                putSerializable(lensStartDateArgKey, pair.rightLens.initDate.millis)
            }
            else -> w("Unexpected position!")
        }
        /* Popolo la mia mappa di appoggio */
        fragmentList[position] = fragment
        return fragment
    }
}