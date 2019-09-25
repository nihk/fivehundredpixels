package nick.photoshowcase.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import nick.core.Event
import nick.core.Resource
import nick.networking.services.PhotosRequest
import nick.photoshowcase.di.InitialPhotosRequest
import nick.photoshowcase.repositories.PhotoShowcaseRepository
import javax.inject.Inject

class PhotoShowcaseViewModel @Inject constructor(
    private val repository: PhotoShowcaseRepository,
    @InitialPhotosRequest
    photosRequest: PhotosRequest
) : ViewModel() {

    private val request = MutableLiveData<PhotosRequest>(photosRequest)

    private val requestValue: PhotosRequest
        get() = requireNotNull(request.value)

    val pageSize: Int
        get() = requestValue.pageSize

    val photos = request.switchMap {
        repository.getPhotos(it, purgeOldData = it.page == 1)
    }

    val error = photos.map {
        if (it is Resource.Error) {
            Event(it.throwable)
        } else {
            null
        }
    }

    fun refresh() {
        setRequest(requestValue.copy(page = 1))
    }

    fun paginate() {
        if (isLoading()) {
            return
        }
        setRequest(requestValue.copy(page = requestValue.page + 1))
    }

    fun isLoading(): Boolean {
        return photos.value is Resource.Loading
    }

    private fun setRequest(photosRequest: PhotosRequest) {
        request.value = photosRequest
    }
}