package nick.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0L,
    val id: Long,
    val name: String,
    val description: String,
    val smallImage: String,
    val largeImage: String,
    val feature: String,
    val width: Int,
    val height: Int,
    val rating: Double
)