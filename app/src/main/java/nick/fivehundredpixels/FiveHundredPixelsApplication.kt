package nick.fivehundredpixels

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import nick.fivehundredpixels.di.DaggerApplicationComponent
import nick.fivehundredpixels.initializers.ApplicationInitializers
import javax.inject.Inject

class FiveHundredPixelsApplication
    : DaggerApplication() {

    @Inject
    lateinit var applicationInitializers: ApplicationInitializers

    override fun onCreate() {
        super.onCreate()
        applicationInitializers.initialize()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory()
            .application(this)
    }
}