package nick.fivehundredpixels

import android.app.Application
import nick.fivehundredpixels.di.ApplicationComponent
import nick.fivehundredpixels.di.ApplicationComponentProvider
import nick.fivehundredpixels.di.DaggerApplicationComponent
import nick.photodetails.di.PhotoDetailsDependencies
import nick.photodetails.di.PhotoDetailsDependenciesProvider
import nick.photoshowcase.di.PhotoShowcaseDependencies
import nick.photoshowcase.di.PhotoShowcaseDependenciesProvider

class FiveHundredPixelsApplication
    : Application()
    , ApplicationComponentProvider
    , PhotoShowcaseDependenciesProvider
    , PhotoDetailsDependenciesProvider {

    override val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory()
            .application(this)
    }
    override val photoShowcaseDependencies: PhotoShowcaseDependencies get() = applicationComponent
    override val photoDetailsDependencies: PhotoDetailsDependencies get() = applicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent.applicationInitializers.initialize()
    }
}