package nick.networking.util

import kotlinx.coroutines.flow.*
import nick.core.Resource

abstract class NetworkBoundResource<T> {

    fun asFlow(): Flow<Resource<T>> = flow {
        val flow = query()
            .onStart { emit(Resource.Loading<T>(null)) }
            .flatMapConcat { data ->
                val flow = query()

                if (shouldFetch(data)) {
                    emit(Resource.Loading(data))

                    try {
                        saveCallResult(fetch())
                        flow.map { Resource.Success(it) }
                    } catch (e: Exception) {
                        onFetchFailed(e)
                        flow.map { Resource.Error(e, it) }
                    }
                } else {
                    flow.map { Resource.Success(it) }
                }
            }

        emitAll(flow)
    }

    abstract fun query(): Flow<T>

    abstract suspend fun fetch(): T

    abstract suspend fun saveCallResult(data: T)

    open fun onFetchFailed(throwable: Throwable) {}

    open fun shouldFetch(data: T) = true
}