package nick.fivehundredpixels.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import nick.core.di.ApplicationScope
import nick.fivehundredpixels.FiveHundredPixelsApplication

@ApplicationScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class
    ]
)
interface ApplicationComponent
    : AndroidInjector<FiveHundredPixelsApplication> {

    @Component.Factory
    interface Factory {
        fun application(@BindsInstance application: Application): ApplicationComponent
    }
}