package nick.photodetails.ui

import androidx.fragment.app.Fragment
import nick.photodetails.vm.PhotoDetailsViewModel
import nick.uiutils.ChildFragmentFactory
import nick.uiutils.ThumbnailZoomCoordinator
import javax.inject.Inject

class PhotoDetailsChildFragmentFactory @Inject constructor(
    private val photoDetailsViewModel: PhotoDetailsViewModel,
    private val thumbnailZoomCoordinator: ThumbnailZoomCoordinator
) : ChildFragmentFactory {

    override fun create(): Fragment {
        return PhotoDetailsFragment(photoDetailsViewModel, thumbnailZoomCoordinator)
    }
}