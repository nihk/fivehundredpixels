package nick.photoshowcase.ui

import androidx.fragment.app.Fragment
import nick.core.Logger
import nick.photoshowcase.vm.PhotoShowcaseViewModel
import nick.uiutils.ChildFragmentFactory
import javax.inject.Inject

class PhotoShowcaseChildFragmentFactory @Inject constructor(
    private val photoShowcaseViewModel: PhotoShowcaseViewModel,
    private val logger: Logger
) : ChildFragmentFactory {

    override fun create(): Fragment {
        return PhotoShowcaseFragment(photoShowcaseViewModel, logger)
    }
}