package nick.photoshowcase.ui

import androidx.recyclerview.widget.DiffUtil
import nick.data.models.Photo

object PhotoDiffCallback
    : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}