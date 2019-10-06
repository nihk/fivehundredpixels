package nick.photoshowcase.di

import nick.core.di.CoreDependencies
import nick.photoshowcase.vm.PhotoShowcaseViewModel

interface PhotoShowcaseDependencies
    : CoreDependencies {

    val photoShowcaseViewModel: PhotoShowcaseViewModel
}