package nick.networking.utils

import kotlinx.coroutines.flow.*
import nick.core.Resource

abstract class NetworkBoundResource<T> {

    fun asFlow() = flow<Resource<T>> {
        emit(Resource.Loading(null))
        val data = query().first()

        val flow = if (shouldFetch(data)) {
            emit(Resource.Loading(data))

            try {
                saveFetchResult(fetch())
                query().map { Resource.Success(it) }
            } catch (throwable: Throwable) {
                onFetchFailed(throwable)
                query().map { Resource.Error(throwable, it) }
            }
        } else {
            query().map { Resource.Success(it) }
        }

        emitAll(flow)
    }

    abstract fun query(): Flow<T>

    abstract suspend fun fetch(): T

    abstract suspend fun saveFetchResult(data: T)

    open fun onFetchFailed(throwable: Throwable) = Unit

    open fun shouldFetch(data: T) = true
}