package nick.photos.daos

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nick.photos.localmodels.Photo

@Dao
interface PhotosDao
    : BaseDao<Photo> {

    @Query("""
        SELECT *
        FROM photos
    """)
    fun queryAll(): Flow<List<Photo>>

    @Query(
        """
        SELECT *
        FROM photos
        WHERE localId = :localId
    """)
    fun queryByLocalId(localId: Long): Flow<Photo?>

    @Query("""
        DELETE
        FROM photos
        WHERE feature = :feature
    """)
    suspend fun deleteByFeature(feature: String)
}