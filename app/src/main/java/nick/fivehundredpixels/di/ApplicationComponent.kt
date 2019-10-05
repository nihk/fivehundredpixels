package nick.fivehundredpixels.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import nick.core.Logger
import nick.core.di.ApplicationScope
import nick.fivehundredpixels.initializers.ApplicationInitializers
import nick.photodetails.di.PhotoDetailsDependencies
import nick.photoshowcase.di.PhotoShowcaseDependencies

@ApplicationScope
@Component(
    modules = [
        ApplicationModule::class
    ]
)
interface ApplicationComponent
    : PhotoShowcaseDependencies
    , PhotoDetailsDependencies {

    val logger: Logger
    val applicationInitializers: ApplicationInitializers

    @Component.Factory
    interface Factory {
        fun application(@BindsInstance application: Application): ApplicationComponent
    }
}