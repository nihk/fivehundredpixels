package nick.networking.services

import nick.data.models.Photo
import retrofit2.http.GET
import retrofit2.http.Query

interface FiveHundredPixelsService {

    companion object {
        const val baseUrl = "https://api.500px.com/v1/"
    }

    @GET("photos")
    suspend fun getPhotos(
        @Query("feature") feature: String?,
        @Query("image_size[]") imageSize: String?,
        @Query("page") page: Int?,
        @Query("rpp") pageSize: Int?,
        @Query("consumer_key") consumerKey: String?
    ): List<Photo>
}