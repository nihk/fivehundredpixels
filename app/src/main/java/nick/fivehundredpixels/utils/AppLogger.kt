package nick.fivehundredpixels.utils

import nick.core.Logger
import timber.log.Timber
import javax.inject.Inject

class AppLogger @Inject constructor()
    : Logger {

    override fun d(message: String) {
        Timber.d(message)
    }

    override fun e(message: String, throwable: Throwable?) {
        Timber.e(throwable, message)
    }
}