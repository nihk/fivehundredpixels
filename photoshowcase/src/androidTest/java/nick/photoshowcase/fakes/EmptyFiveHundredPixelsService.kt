package nick.photoshowcase.fakes

import nick.photos.localmodels.Photo
import nick.photos.services.FiveHundredPixelsService

object EmptyFiveHundredPixelsService :
    FiveHundredPixelsService {

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