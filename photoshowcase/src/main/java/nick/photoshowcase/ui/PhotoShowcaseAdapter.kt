package nick.photoshowcase.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import nick.data.models.Photo
import nick.photoshowcase.R

class PhotoShowcaseAdapter(private val photoShowcaseCallbacks: PhotoShowcaseCallbacks)
    : ListAdapter<Photo, PhotoViewHolder>(PhotoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo, parent, false)
            .let { PhotoViewHolder(it, photoShowcaseCallbacks) }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        if (isLastItem(position)) {
            photoShowcaseCallbacks.paginate()
        }
        holder.bind(getItem(position))
    }

    private fun isLastItem(position: Int) = position == currentList.lastIndex
}