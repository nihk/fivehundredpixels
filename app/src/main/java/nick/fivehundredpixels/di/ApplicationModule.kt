package nick.fivehundredpixels.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import nick.core.di.ApplicationContext
import nick.fivehundredpixels.di.*

@Module(
    includes = [
        DataModule::class,
        NetworkingModule::class,
        InitializerModule::class,
        LoggerModule::class,
        ViewModelsModule::class,
        RequestsModule::class
    ]
)
class ApplicationModule {

    @Provides
    @ApplicationContext
    fun applicationContext(application: Application): Context = application.applicationContext

    @Provides
    fun applicationResources(application: Application): Resources = application.resources
}