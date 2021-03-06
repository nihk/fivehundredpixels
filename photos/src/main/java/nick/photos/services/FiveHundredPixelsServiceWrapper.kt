package nick.photos.services

import nick.photos.localmodels.Photo
import javax.inject.Inject

class FiveHundredPixelsServiceWrapper @Inject constructor(
    private val service: FiveHundredPixelsService
) {
    suspend fun getPhotos(photosRequest: PhotosRequest): List<Photo> {
        return with(photosRequest) {
            service.getPhotos(
                feature = feature,
                imageSize = imageSize,
                page = page,
                pageSize = pageSize,
                consumerKey = consumerKey
            )
        }
    }
}