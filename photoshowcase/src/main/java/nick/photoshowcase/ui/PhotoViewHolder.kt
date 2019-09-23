package nick.photoshowcase.ui

import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_photo.view.*
import nick.data.models.Photo
import nick.uiutils.GlideApp

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
            GlideApp.with(this)
                .load(photo.smallImage)
                .into(this)

            contentDescription = photo.name
        }

        with(constraintSet) {
            clone(itemView.constraint_layout)
            setDimensionRatio(itemView.photo.id, "${photo.width}:${photo.height}")
            applyTo(itemView.constraint_layout)
        }
    }
}