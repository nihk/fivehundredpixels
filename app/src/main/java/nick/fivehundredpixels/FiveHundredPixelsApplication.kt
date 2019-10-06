package nick.fivehundredpixels

import android.app.Application
import nick.fivehundredpixels.di.ApplicationComponent
import nick.fivehundredpixels.di.ApplicationComponentProvider
import nick.fivehundredpixels.di.DaggerApplicationComponent

class FiveHundredPixelsApplication
    : Application()
    , ApplicationComponentProvider {

    override val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory()
            .application(this)
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent.applicationInitializers.initialize()
    }
}