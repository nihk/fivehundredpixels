package nick.fivehundredpixels.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import nick.fivehundredpixels.R
import nick.photoshowcase.ui.OnPhotoClickedListener
import nick.photoshowcase.ui.PhotoShowcaseFragmentDirections

class MainActivity
    : AppCompatActivity(R.layout.activity_main)
    , OnPhotoClickedListener {

    override fun onPhotoClicked(id: Long) {
        findNavController(R.id.five_hundred_pixels_host)
            .navigate(PhotoShowcaseFragmentDirections.toDetails(id))
    }
}