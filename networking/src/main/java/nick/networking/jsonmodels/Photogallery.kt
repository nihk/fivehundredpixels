package nick.networking.jsonmodels

data class Photogallery(
    val current_page: Int,
    val total_pages: Int,
    val feature: String,
    val photos: List<Photo>
)