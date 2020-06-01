package nick.photos.localmodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "photos",
    indices = [Index(value = [Photo.columnRemoteId], unique = true)]
)
data class Photo(
    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0L,
    @ColumnInfo(name = columnRemoteId)
    val remoteId: Long,
    val name: String,
    val description: String,
    val smallImage: String,
    val largeImage: String,
    val feature: String,
    val width: Int,
    val height: Int,
    val rating: Double
) {
    companion object {
        internal const val columnRemoteId = "remoteId"
    }
}