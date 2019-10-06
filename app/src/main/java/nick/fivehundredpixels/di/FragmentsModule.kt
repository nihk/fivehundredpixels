package nick.fivehundredpixels.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import nick.fivehundredpixels.ui.ApplicationFragmentFactory
import nick.photodetails.ui.PhotoDetailsFragment
import nick.photoshowcase.ui.PhotoShowcaseFragment

@Module
abstract class FragmentsModule {

    @Binds
    abstract fun fragmentFactory(applicationFragmentFactory: ApplicationFragmentFactory): FragmentFactory

    @Binds
    @IntoMap
    @FragmentKey(PhotoDetailsFragment::class)
    abstract fun photoDetailsFragment(
        photoDetailsFragment: PhotoDetailsFragment
    ): Fragment

    @Binds
    @IntoMap
    @FragmentKey(PhotoShowcaseFragment::class)
    abstract fun photoShowcaseFragment(
        photoShowcaseFragment: PhotoShowcaseFragment
    ): Fragment
}