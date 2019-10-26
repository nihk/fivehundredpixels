package nick.networking.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import nick.core.Resource

// Adapted from: https://developer.android.com/topic/libraries/architecture/coroutines
abstract class NetworkBoundResource<T> {

    fun asLiveData(): LiveData<Resource<T>> {
        return liveData<Resource<T>> {
            emit(Resource.Loading(null))

            if (shouldFetch(query())) {
                val disposable = emitSource(queryObservable().map { Resource.Loading(it) })

                try {
                    val fetchedData = fetch()
                    // Stop the previous emission to avoid dispatching the saveCallResult as `Resource.Loading`.
                    disposable.dispose()
                    saveCallResult(fetchedData)
                    // Re-establish the emission as `Resource.Success`.
                    emitSource(queryObservable().map { Resource.Success(it) })
                } catch (throwable: Throwable) {
                    onFetchFailed(throwable)
                    emitSource(queryObservable().map { Resource.Error(throwable, it) })
                }
            } else {
                emitSource(queryObservable().map { Resource.Success(it) })
            }
        }
    }

    abstract suspend fun query(): T?

    abstract fun queryObservable(): LiveData<T>

    abstract suspend fun fetch(): T

    abstract suspend fun saveCallResult(data: T)

    open fun onFetchFailed(throwable: Throwable) {}

    open fun shouldFetch(data: T?) = true
}