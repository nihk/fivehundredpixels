package nick.photoshowcase.ui

import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import kotlinx.android.synthetic.main.item_photo.view.*
import nick.data.models.Photo

class PhotoViewHolder(
    view: View,
    private val onPhotoClickedListener: OnPhotoClickedListener
) : RecyclerView.ViewHolder(view) {

    private val constraintSet = ConstraintSet()

    fun bind(photo: Photo) {
        itemView.setOnClickListener {
            onPhotoClickedListener.onPhotoClicked(photo.id)
        }

        with(itemView.photo) {
            load(photo.smallImage)
            contentDescription = photo.name
        }

        with(constraintSet) {
            clone(itemView.constraint_layout)
            setDimensionRatio(itemView.photo.id, "${photo.width}:${photo.height}")
            applyTo(itemView.constraint_layout)
        }
    }
}