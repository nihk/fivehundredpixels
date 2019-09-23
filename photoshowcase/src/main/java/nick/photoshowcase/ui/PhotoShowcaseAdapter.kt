package nick.photoshowcase.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import nick.data.models.Photo
import nick.photoshowcase.R

class PhotoShowcaseAdapter(private val onPhotoClickedListener: OnPhotoClickedListener)
    : ListAdapter<Photo, PhotoViewHolder>(PhotoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo, parent, false)
            .let { PhotoViewHolder(it, onPhotoClickedListener) }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}