package nick.photoshowcase.repositories

import androidx.lifecycle.LiveData
import nick.core.Logger
import nick.core.Resource
import nick.data.daos.PhotosDao
import nick.data.models.Photo
import nick.networking.services.FiveHundredPixelsService
import nick.networking.services.PhotosRequest
import nick.networking.util.NetworkBoundResource
import javax.inject.Inject

class PhotoShowcaseRepository @Inject constructor(
    private val fiveHundredPixelsService: FiveHundredPixelsService,
    private val photosDao: PhotosDao,
    private val logger: Logger
) {

    fun getPhotos(photosRequest: PhotosRequest, purgeOldData: Boolean): LiveData<Resource<List<Photo>>> {
        return object : NetworkBoundResource<List<Photo>>() {

            override suspend fun query(): List<Photo> {
                return photosDao.queryAll()
            }

            override fun queryObservable(): LiveData<List<Photo>> {
                return photosDao.queryAllObservable()
            }

            override suspend fun fetch(): List<Photo> {
                logger.d("Fetching $photosRequest")
                with(photosRequest) {
                    return fiveHundredPixelsService.getPhotos(
                        feature = feature,
                        imageSize = imageSize,
                        page = page,
                        pageSize = pageSize,
                        consumerKey = consumerKey
                    )
                }
            }

            override suspend fun saveCallResult(data: List<Photo>) {
                if (purgeOldData) {
                    photosRequest.feature?.let {
                        photosDao.deleteByFeature(it)
                    }
                }
                photosDao.insert(data)
            }

            override fun onFetchFailed(throwable: Throwable) {
                logger.e("Fetching photos failed", throwable)
            }
        }.asLiveData()
    }
}