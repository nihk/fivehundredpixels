package nick.fivehundredpixels.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nick.photodetails.ui.PhotoDetailsFragment
import nick.photoshowcase.ui.PhotoShowcaseFragment

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun photoShowcaseFragment(): PhotoShowcaseFragment

    @ContributesAndroidInjector
    fun photoDetailsFragment(): PhotoDetailsFragment
}