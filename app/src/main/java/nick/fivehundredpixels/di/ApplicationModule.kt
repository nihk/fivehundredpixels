package nick.fivehundredpixels.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import nick.core.di.ApplicationContext

@Module
class ApplicationModule {

    @Provides
    @ApplicationContext
    fun applicationContext(application: Application): Context = application.applicationContext

    @Provides
    fun applicationResources(application: Application): Resources = application.resources
}