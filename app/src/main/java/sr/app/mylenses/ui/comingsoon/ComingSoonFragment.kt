package sr.app.mylenses.ui.comingsoon

import android.os.Bundle
import com.google.android.material.transition.MaterialContainerTransform
import sr.app.mylenses.BaseFragment
import sr.app.mylenses.databinding.ComingSoonFragmentBinding

class ComingSoonFragment :
    BaseFragment<ComingSoonFragmentBinding>(ComingSoonFragmentBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 600
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
        }
    }
}