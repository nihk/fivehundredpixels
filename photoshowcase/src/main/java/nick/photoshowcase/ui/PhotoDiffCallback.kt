package nick.photoshowcase.ui

import androidx.recyclerview.widget.DiffUtil
import nick.photos.localmodels.Photo

object PhotoDiffCallback
    : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.remoteId == newItem.remoteId
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.name == newItem.name
                && oldItem.description == newItem.description
                && oldItem.smallImage == newItem.smallImage
                && oldItem.width == newItem.width
                && oldItem.height == newItem.height
    }
}