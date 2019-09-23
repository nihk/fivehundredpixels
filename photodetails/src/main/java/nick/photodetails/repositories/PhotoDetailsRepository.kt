package nick.photodetails.repositories

import androidx.lifecycle.LiveData
import dagger.Reusable
import nick.data.daos.PhotosDao
import nick.data.models.Photo
import javax.inject.Inject

@Reusable
class PhotoDetailsRepository @Inject constructor(
    private val photosDao: PhotosDao
) {

    fun getPhotoById(id: Long): LiveData<Photo?> {
        return photosDao.queryById(id)
    }
}