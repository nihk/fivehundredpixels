package nick.photoshowcase

import android.app.Application
import nick.photoshowcase.di.PhotoShowcaseDependencies
import nick.photoshowcase.di.PhotoShowcaseDependenciesProvider

class TestApplication
    : Application()
    , PhotoShowcaseDependenciesProvider {

    lateinit var photoShowcaseDependenciesProvider: PhotoShowcaseDependenciesProvider

    override val photoShowcaseDependencies: PhotoShowcaseDependencies
        get() = photoShowcaseDependenciesProvider.photoShowcaseDependencies
}