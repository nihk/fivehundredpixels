package nick.photodetails.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import nick.photodetails.repositories.PhotoDetailsRepository
import javax.inject.Inject

class PhotoDetailsViewModel @Inject constructor(
    private val repository: PhotoDetailsRepository
) : ViewModel() {

    private val queryId = MutableLiveData<Long>()

    val photo = queryId.switchMap(repository::getPhotoById)

    fun queryPhoto(id: Long) {
        queryId.value = id
    }
}