package nick.testutils

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector

class InjectorApplication
    : Application()
    , HasAndroidInjector {

    lateinit var injector: AndroidInjector<Any>

    override fun androidInjector() = injector
}