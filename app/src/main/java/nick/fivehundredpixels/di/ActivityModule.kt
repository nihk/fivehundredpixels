package nick.fivehundredpixels.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nick.fivehundredpixels.ui.MainActivity

@Module
interface ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun mainActivity(): MainActivity
}