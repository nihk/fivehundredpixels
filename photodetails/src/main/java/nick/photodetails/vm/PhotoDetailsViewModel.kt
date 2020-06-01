package nick.photodetails.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import nick.photos.daos.PhotosDao
import nick.photos.localmodels.Photo
import javax.inject.Inject

class PhotoDetailsViewModel @Inject constructor(
    private val photosDao: PhotosDao
) : ViewModel() {

    fun getPhoto(id: Long): Flow<Photo?> {
        return photosDao.queryByLocalId(id)
    }
}