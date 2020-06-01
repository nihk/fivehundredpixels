package nick.photoshowcase.repositories

import kotlinx.coroutines.flow.Flow
import nick.core.Logger
import nick.core.Resource
import nick.data.daos.PhotosDao
import nick.data.models.Photo
import nick.networking.services.FiveHundredPixelsServiceWrapper
import nick.networking.services.PhotosRequest
import nick.networking.utils.networkBoundResource
import javax.inject.Inject

class PhotoShowcaseRepository @Inject constructor(
    private val serviceWrapper: FiveHundredPixelsServiceWrapper,
    private val photosDao: PhotosDao,
    private val logger: Logger
) {
    fun getPhotos(
        photosRequest: PhotosRequest,
        purgeOldData: Boolean
    ): Flow<Resource<List<Photo>>> {
        return networkBoundResource(
            query = { photosDao.queryAll() },
            fetch = {
                logger.d("Fetching $photosRequest")
                serviceWrapper.getPhotos(photosRequest)
            },
            onFetchSucceeded = { data ->
                if (purgeOldData) {
                    photosRequest.feature?.let {
                        photosDao.deleteByFeature(it)
                    }
                }
                photosDao.insert(data)
            },
            onFetchFailed = { throwable -> logger.e("Fetching photos failed", throwable) }
        )
    }
}