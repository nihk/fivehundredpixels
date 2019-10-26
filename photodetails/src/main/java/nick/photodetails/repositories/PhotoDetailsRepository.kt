package nick.photodetails.repositories

import kotlinx.coroutines.flow.Flow
import nick.data.daos.PhotosDao
import nick.data.models.Photo
import javax.inject.Inject

class PhotoDetailsRepository @Inject constructor(
    private val photosDao: PhotosDao
) {

    fun getPhotoById(id: Long): Flow<Photo?> {
        return photosDao.queryById(id)
    }
}