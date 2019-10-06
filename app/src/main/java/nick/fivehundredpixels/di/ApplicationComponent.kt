package nick.fivehundredpixels.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import nick.core.di.ApplicationScope
import nick.fivehundredpixels.initializers.ApplicationInitializers
import nick.fivehundredpixels.ui.ApplicationFragmentFactory

@ApplicationScope
@Component(
    modules = [
        ApplicationModule::class
    ]
)
interface ApplicationComponent {

    val applicationInitializers: ApplicationInitializers
    val applicationFragmentFactory: ApplicationFragmentFactory

    @Component.Factory
    interface Factory {
        fun application(@BindsInstance application: Application): ApplicationComponent
    }
}