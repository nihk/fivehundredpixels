package nick.uiutils

import android.graphics.Matrix
import android.graphics.drawable.Drawable
import com.github.chrisbanes.photoview.PhotoView
import javax.inject.Inject

class ThumbnailZoomCoordinator @Inject constructor() {

    fun setImageDrawable(photoView: PhotoView, drawable: Drawable) {
        with(photoView) {
            val matrix = Matrix()
            attacher.getSuppMatrix(matrix)
            setImageDrawable(drawable)
            attacher.setDisplayMatrix(matrix)
        }
    }
}