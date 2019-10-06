package nick.fivehundredpixels.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import nick.uiutils.ChildFragmentFactory
import javax.inject.Inject
import javax.inject.Provider

class ApplicationFragmentFactory @Inject constructor(
    private val childFragmentFactories: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<ChildFragmentFactory>>
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragmentClass: Class<out Fragment> = loadFragmentClass(classLoader, className)
        return childFragmentFactories[fragmentClass]?.get()?.create()
            ?: super.instantiate(classLoader, className)
    }
}