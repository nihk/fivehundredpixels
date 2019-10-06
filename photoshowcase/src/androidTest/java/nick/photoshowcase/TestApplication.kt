package nick.photoshowcase

import android.app.Application
import nick.photoshowcase.ui.PhotoShowcaseFragment

class TestApplication
    : Application() {

    lateinit var photoShowcaseFragment: PhotoShowcaseFragment
}