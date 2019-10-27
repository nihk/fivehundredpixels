package nick.fivehundredpixels.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import nick.networking.services.PhotosRequest
import nick.photoshowcase.di.InitialPhotosRequest

@Module
object RequestsModule {

    @Reusable
    @Provides
    @InitialPhotosRequest
    fun initialPhotoShowcaseRequest(): PhotosRequest {
        return PhotosRequest(
            feature = "popular",
            imageSize = "31,1080",
            page = 1,
            pageSize = 40
        )
    }
}