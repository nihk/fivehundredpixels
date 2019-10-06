package nick.fivehundredpixels.di

import androidx.fragment.app.FragmentFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import nick.fivehundredpixels.ui.ApplicationFragmentFactory
import nick.photodetails.ui.PhotoDetailsChildFragmentFactory
import nick.photodetails.ui.PhotoDetailsFragment
import nick.photoshowcase.ui.PhotoShowcaseChildFragmentFactory
import nick.photoshowcase.ui.PhotoShowcaseFragment
import nick.uiutils.ChildFragmentFactory

@Module
abstract class FragmentFactoriesModule {

    @Binds
    abstract fun fragmentFactory(applicationFragmentFactory: ApplicationFragmentFactory): FragmentFactory

    @Binds
    @IntoMap
    @FragmentKey(PhotoDetailsFragment::class)
    abstract fun photoDetailsChildFragmentFactory(
        photoDetailsChildFragmentFactory: PhotoDetailsChildFragmentFactory
    ): ChildFragmentFactory

    @Binds
    @IntoMap
    @FragmentKey(PhotoShowcaseFragment::class)
    abstract fun photoShowcaseChildFragmentFactory(
        photoShowcaseChildFragmentFactory: PhotoShowcaseChildFragmentFactory
    ): ChildFragmentFactory
}