package nick.photoshowcase.fakes

import nick.data.models.Photo
import nick.networking.services.FiveHundredPixelsService

object EmptyFiveHundredPixelsService : FiveHundredPixelsService {

    override suspend fun getPhotos(
        feature: String?,
        imageSize: String?,
        page: Int?,
        pageSize: Int?,
        consumerKey: String?
    ): List<Photo> {
        return emptyList()
    }
}