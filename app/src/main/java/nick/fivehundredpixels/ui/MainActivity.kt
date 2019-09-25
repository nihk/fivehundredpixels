package nick.fivehundredpixels.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import nick.core.Logger
import nick.photoshowcase.ui.OnPhotoClickedListener
import nick.photoshowcase.ui.PhotoShowcaseFragmentDirections
import nick.fivehundredpixels.R
import javax.inject.Inject

class MainActivity
    : AppCompatActivity(R.layout.activity_main)
    , HasAndroidInjector
    , OnPhotoClickedListener {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    @Inject
    lateinit var logger: Logger

    override fun androidInjector() = androidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onPhotoClicked(id: Long) {
        findNavController(R.id.five_hundred_pixels_host)
            .navigate(PhotoShowcaseFragmentDirections.toDetails(id))
        logger.d("Navigating to photo with id $id")
    }
}