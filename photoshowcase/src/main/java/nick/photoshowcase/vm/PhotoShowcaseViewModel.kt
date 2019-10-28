package nick.photoshowcase.vm

import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nick.core.Event
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

    private var fetchPhotosJob: Job? = null

    private val _photos = MutableLiveData<Resource<List<Photo>>>()
    val photos: LiveData<Resource<List<Photo>>> = _photos

    val error = photos.map {
        if (it is Resource.Error) {
            Event(it.throwable)
        } else {
            null
        }
    }

    fun refresh() {
        setPhotosRequest(photosRequest.copy(page = 1))
    }

    fun paginate() {
        setPhotosRequest(photosRequest.copy(page = photosRequest.page + 1))
    }

    private fun setPhotosRequest(photosRequest: PhotosRequest) {
        this.photosRequest = photosRequest

        fetchPhotosJob?.cancel()
        fetchPhotosJob = viewModelScope.launch {
            repository.getPhotos(photosRequest, purgeOldData = photosRequest.page == 1).collect {
                _photos.value = it
            }
        }
    }
}