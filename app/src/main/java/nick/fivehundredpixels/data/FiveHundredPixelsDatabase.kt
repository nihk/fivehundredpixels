package nick.fivehundredpixels.data

import androidx.room.Database
import androidx.room.RoomDatabase
import nick.photos.daos.PhotosDao
import nick.photos.localmodels.Photo

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