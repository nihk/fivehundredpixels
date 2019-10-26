package nick.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nick.data.models.Photo

@Dao
interface PhotosDao
    : BaseDao<Photo> {

    @Query("""
        SELECT *
        FROM photos
    """)
    fun queryAllObservable(): LiveData<List<Photo>>

    @Query("""
        SELECT *
        FROM photos
    """)
    suspend fun queryAll(): List<Photo>

    @Query("""
        SELECT *
        FROM photos
        WHERE id = :id
    """)
    fun queryById(id: Long): Flow<Photo?>

    @Query("""
        DELETE
        FROM photos
        WHERE feature = :feature
    """)
    suspend fun deleteByFeature(feature: String)
}