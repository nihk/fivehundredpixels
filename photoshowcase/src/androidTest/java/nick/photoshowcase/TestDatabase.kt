package nick.photoshowcase

import androidx.room.Database
import androidx.room.RoomDatabase
import nick.photos.daos.PhotosDao
import nick.photos.localmodels.Photo

@Database(
    entities = [Photo::class],
    version = 1,
    exportSchema = false
)
abstract class TestDatabase
    : RoomDatabase() {

    abstract fun photosDao(): PhotosDao
}