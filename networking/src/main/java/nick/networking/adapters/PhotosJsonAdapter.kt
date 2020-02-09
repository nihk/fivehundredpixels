package nick.networking.adapters

import com.squareup.moshi.FromJson
import nick.data.models.Photo
import nick.networking.jsonmodels.Photogallery
import javax.inject.Inject

class PhotosJsonAdapter @Inject constructor() {

    @FromJson
    fun fromJson(photogallery: Photogallery): List<Photo> {
        return photogallery.photos.map {
            Photo(
                remoteId = it.id,
                name = it.name,
                description = it.description,
                smallImage = it.image_url.first(),
                largeImage = it.image_url.last(),
                feature = photogallery.feature,
                width = it.width,
                height = it.height,
                rating = it.rating
            )
        }
    }
}