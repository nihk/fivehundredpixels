package nick.photoshowcase.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import nick.core.Resource
import nick.data.models.Photo
import nick.networking.services.PhotosRequest
import nick.photoshowcase.di.InitialPhotosRequest
import nick.photoshowcase.repositories.PhotoShowcaseRepository
import javax.inject.Inject

class PhotoShowcaseViewModel @Inject constructor(
    private val repository: PhotoShowcaseRepository,
    @InitialPhotosRequest
    private var photosRequest: PhotosRequest
) : ViewModel() {

    fun refresh(): Flow<Resource<List<Photo>>> {
        return setPhotosRequest(photosRequest.copy(page = 1))
    }

    fun paginate(): Flow<Resource<List<Photo>>> {
        return setPhotosRequest(photosRequest.copy(page = photosRequest.page + 1))
    }

    private fun setPhotosRequest(photosRequest: PhotosRequest): Flow<Resource<List<Photo>>> {
        this.photosRequest = photosRequest
        return repository.getPhotos(photosRequest, purgeOldData = photosRequest.page == 1)
    }
}