package nick.uiutils

import androidx.fragment.app.Fragment

interface ChildFragmentFactory {

    fun create(): Fragment
}