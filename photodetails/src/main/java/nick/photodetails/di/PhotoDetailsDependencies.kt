package nick.photodetails.di

import nick.core.di.CoreDependencies
import nick.photodetails.vm.PhotoDetailsViewModel
import nick.uiutils.ThumbnailZoomCoordinator

interface PhotoDetailsDependencies
    : CoreDependencies {

    val photoDetailsViewModel: PhotoDetailsViewModel
    val thumbnailZoomCoordinator: ThumbnailZoomCoordinator
}