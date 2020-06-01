package nick.photoshowcase.repositories

import kotlinx.coroutines.flow.Flow
import nick.core.Logger
import nick.core.Resource
import nick.photos.daos.PhotosDao
import nick.photos.localmodels.Photo
import nick.photos.services.FiveHundredPixelsServiceWrapper
import nick.photos.services.PhotosRequest
import nick.core.networkBoundResource
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