package nick.fivehundredpixels.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.multibindings.IntoMap
import nick.photodetails.vm.PhotoDetailsViewModel
import nick.photoshowcase.vm.PhotoShowcaseViewModel
import nick.fivehundredpixels.vm.ViewModelFactory

@Module
abstract class ViewModelsModule {

    @Reusable
    @Binds
    abstract fun viewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PhotoShowcaseViewModel::class)
    abstract fun photoShowcaseViewModel(photoShowcaseViewModel: PhotoShowcaseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PhotoDetailsViewModel::class)
    abstract fun photoDetailsViewModel(photoDetailsViewModel: PhotoDetailsViewModel): ViewModel
}