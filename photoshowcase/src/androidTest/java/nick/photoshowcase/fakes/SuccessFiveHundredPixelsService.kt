package nick.photoshowcase.fakes

import nick.data.models.Photo
import nick.networking.services.FiveHundredPixelsService

object SuccessFiveHundredPixelsService : FiveHundredPixelsService {

    override suspend fun getPhotos(
        feature: String?,
        imageSize: String?,
        page: Int?,
        pageSize: Int?,
        consumerKey: String?
    ): List<Photo> {
        return photos
    }
}