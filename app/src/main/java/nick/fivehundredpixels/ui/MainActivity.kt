package nick.fivehundredpixels.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import nick.fivehundredpixels.R
import nick.fivehundredpixels.di.ApplicationComponentProvider
import nick.photoshowcase.ui.OnPhotoClickedListener
import nick.photoshowcase.ui.PhotoShowcaseFragmentDirections

class MainActivity
    : AppCompatActivity(R.layout.activity_main)
    , OnPhotoClickedListener {

    private val component by lazy {
        (application as ApplicationComponentProvider).applicationComponent
    }
    private val logger by lazy { component.logger }

    override fun onPhotoClicked(id: Long) {
        findNavController(R.id.five_hundred_pixels_host)
            .navigate(PhotoShowcaseFragmentDirections.toDetails(id))
        logger.d("Navigating to photo with id $id")
    }
}