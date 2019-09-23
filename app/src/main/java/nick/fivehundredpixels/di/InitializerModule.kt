package nick.fivehundredpixels.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import nick.fivehundredpixels.initializers.Initializer
import nick.fivehundredpixels.initializers.TimberInitializer

@Module
abstract class InitializerModule {

    @Binds
    @IntoSet
    abstract fun timberInitializer(timberInitializer: TimberInitializer): Initializer
}