package nick.fivehundredpixels.data

import androidx.room.Database
import androidx.room.RoomDatabase
import nick.data.models.Photo
import nick.data.daos.PhotosDao

@Database(
    entities = [Photo::class],
    version = 1
)
abstract class FiveHundredPixelsDatabase
    : RoomDatabase() {

    abstract fun photosDao(): PhotosDao

    companion object {
        const val databaseName = "500px.db"
    }
}