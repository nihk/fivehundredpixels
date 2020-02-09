package nick.fivehundredpixels.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import nick.fivehundredpixels.R
import nick.photodetails.ui.PhotoDetailsFragmentArgs
import nick.photoshowcase.ui.OnPhotoClickedListener

class MainActivity
    : AppCompatActivity(R.layout.activity_main)
    , OnPhotoClickedListener {

    override fun onPhotoClicked(id: Long) {
        findNavController(R.id.five_hundred_pixels_host)
            .navigate(
                R.id.photodetails_graph,
                PhotoDetailsFragmentArgs.Builder(id)
                    .build()
                    .toBundle(),
                NavOptions.Builder()
                    .setEnterAnim(R.anim.nav_default_enter_anim)
                    .setExitAnim(R.anim.nav_default_exit_anim)
                    .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                    .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
                    .build()
            )
    }
}