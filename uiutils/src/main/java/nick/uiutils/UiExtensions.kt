package nick.uiutils

import android.app.Application
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visibleOrGone(shouldBeVisible: Boolean) {
    if (shouldBeVisible) {
        visible()
    } else {
        gone()
    }
}

inline fun <reified T : ViewModel> Fragment.viewModel(
    crossinline provider: () -> T
) = viewModels<T> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return provider() as T
        }
    }
}

inline fun <reified T : ViewModel> Fragment.activityViewModel(
    crossinline provider: () -> T
) = activityViewModels<T> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return provider() as T
        }
    }
}

val Fragment.activityApplication: Application get() = requireActivity().application