package nick.photodetails.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import nick.data.models.Photo
import nick.photodetails.repositories.PhotoDetailsRepository
import javax.inject.Inject

class PhotoDetailsViewModel @Inject constructor(
    private val repository: PhotoDetailsRepository
) : ViewModel() {

    fun getPhoto(id: Long): Flow<Photo?> {
        return repository.getPhotoById(id)
    }
}