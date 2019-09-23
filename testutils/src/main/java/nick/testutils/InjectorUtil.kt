package nick.testutils

import dagger.android.AndroidInjector
import dagger.android.AndroidInjector.Factory
import dagger.android.DispatchingAndroidInjector
import dagger.android.DispatchingAndroidInjector_Factory
import dagger.android.HasAndroidInjector
import javax.inject.Provider

inline fun <reified T : HasAndroidInjector> createFakeInjector(crossinline block: T.() -> Unit)
        : DispatchingAndroidInjector<Any> {
    val injector = AndroidInjector<HasAndroidInjector> { instance ->
        if (instance is T) {
            instance.block()
        }
    }
    val factory = Factory<HasAndroidInjector> { injector }
    val map = mapOf(Pair<Class<*>, Provider<Factory<*>>>(T::class.java, Provider { factory }))
    return DispatchingAndroidInjector_Factory.newInstance(map, emptyMap())
}