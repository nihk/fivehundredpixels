package nick.fivehundredpixels.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import nick.networking.services.PhotosRequest
import nick.photoshowcase.di.InitialPhotosRequest
import nick.fivehundredpixels.BuildConfig

@Module
object RequestsModule {

    @Reusable
    @Provides
    @InitialPhotosRequest
    @JvmStatic
    fun initialPhotoShowcaseRequest(): PhotosRequest {
        return PhotosRequest(
            feature = "popular",
            imageSize = "31,1080",
            page = 1,
            pageSize = 40,
            consumerKey = BuildConfig.FIVE_HUNDRED_PIXELS_API_KEY
        )
    }
}