package nick.fivehundredpixels.di

import dagger.Binds
import dagger.Module
import dagger.Reusable
import nick.core.Logger
import nick.fivehundredpixels.utils.AppLogger

@Module
abstract class LoggerModule {

    @Reusable
    @Binds
    abstract fun logger(appLogger: AppLogger): Logger
}