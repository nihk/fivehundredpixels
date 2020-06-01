package nick.networking.utils

import kotlinx.coroutines.flow.*
import nick.core.Resource

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline onFetchSucceeded: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { Unit },
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow<Resource<ResultType>> {
    emit(Resource.Loading())

    val data = query().first()
    val flow: Flow<Resource<ResultType>> = if (shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            onFetchSucceeded(fetch())
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