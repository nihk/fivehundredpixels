package nick.photoshowcase.vm

import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nick.core.Resource
import nick.data.models.Photo
import nick.networking.services.PhotosRequest
import nick.photoshowcase.di.InitialPhotosRequest
import nick.photoshowcase.di.PaginationThreshold
import nick.photoshowcase.repositories.PhotoShowcaseRepository
import javax.inject.Inject

class PhotoShowcaseViewModel @Inject constructor(
    private val repository: PhotoShowcaseRepository,
    @InitialPhotosRequest
    private var photosRequest: PhotosRequest,
    @PaginationThreshold
    private val paginationThreshold: Int
) : ViewModel() {

    private var fetchPhotosJob: Job? = null

    private val _photosState = MutableLiveData<Resource<List<Photo>>>()

    val photos: MediatorLiveData<List<Photo>> = MediatorLiveData<List<Photo>>().apply {
        addSource(_photosState) {
            if (it is Resource.Success || it is Resource.Error) {
                value = it.data
            }
        }
    }

    val error = _photosState.map {
        if (it is Resource.Error) {
            it.throwable
        } else {
            null
        }
    }

    val loading = _photosState.map { it is Resource.Loading && photosRequest.page == 1 }

    fun refresh() {
        setPhotosRequest(photosRequest.copy(page = 1))
    }

    fun paginate() {
        if (_photosState.value !is Resource.Success) {
            return
        }

        setPhotosRequest(photosRequest.copy(page = photosRequest.page + 1))
    }

    fun paginationThreshold() = paginationThreshold

    fun retry() {
        setPhotosRequest(photosRequest)
    }

    private fun setPhotosRequest(photosRequest: PhotosRequest) {
        fetchPhotosJob?.cancel()
        fetchPhotosJob = viewModelScope.launch {
            this@PhotoShowcaseViewModel.photosRequest = photosRequest

            repository.getPhotos(photosRequest, purgeOldData = photosRequest.page == 1).collect {
                _photosState.value = it
            }
        }
    }
}