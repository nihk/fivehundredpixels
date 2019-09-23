package nick.networking.services

data class PhotosRequest(
    val feature: String? = null,
    val imageSize: String? = null,
    val page: Int = 1,
    val pageSize: Int = 20,
    val consumerKey: String? = null
)