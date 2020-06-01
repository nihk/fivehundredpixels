package nick.photos.jsonmodels

data class Photo(
    val id: Long,
    val name: String,
    val description: String,
    val image_url: List<String>,
    val width: Int,
    val height: Int,
    val rating: Double
)