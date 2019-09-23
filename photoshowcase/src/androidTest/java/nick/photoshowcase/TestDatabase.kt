package nick.photoshowcase

import androidx.room.Database
import androidx.room.RoomDatabase
import nick.data.daos.PhotosDao
import nick.data.models.Photo

@Database(
    entities = [Photo::class],
    version = 1,
    exportSchema = false
)
abstract class TestDatabase
    : RoomDatabase() {

    abstract fun photosDao(): PhotosDao
}