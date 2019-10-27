package nick.photodetails.vm

import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nick.data.models.Photo
import nick.photodetails.repositories.PhotoDetailsRepository
import javax.inject.Inject

class PhotoDetailsViewModel @Inject constructor(
    private val repository: PhotoDetailsRepository
) : ViewModel() {

    private val _photo = MutableLiveData<Photo?>()
    val photo: LiveData<Photo?> = _photo

    fun queryPhoto(id: Long) {
        viewModelScope.launch {
            repository.getPhotoById(id).collect {
                _photo.value = it
            }
        }
    }
}