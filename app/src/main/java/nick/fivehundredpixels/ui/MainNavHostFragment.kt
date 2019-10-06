package nick.fivehundredpixels.ui

import android.content.Context
import androidx.navigation.fragment.NavHostFragment
import nick.fivehundredpixels.di.ApplicationComponentProvider
import nick.uiutils.activityApplication

class MainNavHostFragment : NavHostFragment() {

    override fun onAttach(context: Context) {
        childFragmentManager.fragmentFactory =
            (activityApplication as ApplicationComponentProvider).applicationComponent.applicationFragmentFactory
        super.onAttach(context)
    }
}