package nick.photodetails.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import nick.data.daos.PhotosDao
import nick.data.models.Photo
import javax.inject.Inject

class PhotoDetailsViewModel @Inject constructor(
    private val photosDao: PhotosDao
) : ViewModel() {

    fun getPhoto(id: Long): Flow<Photo?> {
        return photosDao.queryById(id)
    }
}