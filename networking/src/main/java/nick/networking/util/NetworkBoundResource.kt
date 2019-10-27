package nick.networking.util

import kotlinx.coroutines.flow.*
import nick.core.Resource

abstract class NetworkBoundResource<T> {

    fun asFlow(): Flow<Resource<T>> = flow {
        val flow = query()
            .onStart { emit(Resource.Loading<T>(null)) }
            .flatMapConcat { data ->
                if (shouldFetch(data)) {
                    emit(Resource.Loading(data))

                    try {
                        saveCallResult(fetch())
                        query().map { Resource.Success(it) }
                    } catch (e: Exception) {
                        onFetchFailed(e)
                        query().map { Resource.Error(e, it) }
                    }
                } else {
                    query().map { Resource.Success(it) }
                }
            }

        emitAll(flow)
    }

    abstract fun query(): Flow<T>

    abstract suspend fun fetch(): T

    abstract suspend fun saveCallResult(data: T)

    open fun onFetchFailed(throwable: Throwable) = Unit

    open fun shouldFetch(data: T) = true
}