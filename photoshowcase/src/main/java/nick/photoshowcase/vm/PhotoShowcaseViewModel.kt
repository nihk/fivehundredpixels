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
    private var photosRequest: PhotosRequest
) : ViewModel() {

    val pageSize: Int
        get() = photosRequest.pageSize

    private val request = MutableLiveData<PhotosRequest>()

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
        setRequest(photosRequest.copy(page = 1))
    }

    fun paginate() {
        if (isPaginating()) {
            return
        }
        setRequest(photosRequest.copy(page = photosRequest.page + 1))
    }

    fun isPaginating(): Boolean {
        return photos.value is Resource.Loading
    }

    private fun setRequest(photosRequest: PhotosRequest) {
        this.photosRequest = photosRequest
        request.value = this.photosRequest
    }
}