package nick.photoshowcase.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import nick.data.models.Photo
import nick.photoshowcase.R

class PhotoShowcaseAdapter(
    private val callbacks: PhotoShowcaseCallbacks
) : ListAdapter<Photo, PhotoViewHolder>(PhotoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo, parent, false)
            .let { PhotoViewHolder(it, callbacks) }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        if (shouldPaginate(position)) {
            callbacks.paginate()
        }
        holder.bind(getItem(position))
    }

    private fun shouldPaginate(position: Int) = position >= currentList.lastIndex - callbacks.paginationThreshold()
}