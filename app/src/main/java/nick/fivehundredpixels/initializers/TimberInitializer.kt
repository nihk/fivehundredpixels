package nick.fivehundredpixels.initializers

import nick.fivehundredpixels.initializers.Initializer
import nick.fivehundredpixels.BuildConfig
import timber.log.Timber
import javax.inject.Inject

class TimberInitializer @Inject constructor()
    : Initializer {

    override fun initialize() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}