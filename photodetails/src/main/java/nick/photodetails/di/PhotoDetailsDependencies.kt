package nick.photodetails.di

import nick.core.di.CoreDependencies
import nick.photodetails.vm.PhotoDetailsViewModel

interface PhotoDetailsDependencies
    : CoreDependencies {

    val photoDetailsViewModel: PhotoDetailsViewModel
}