package nick.core

sealed class Resource<T> {
    abstract val data: T?

    data class Loading<T>(override val data: T? = null) : Resource<T>()
    data class Success<T>(override val data: T? = null) : Resource<T>()
    data class Error<T>(val throwable: Throwable, override val data: T? = null) : Resource<T>()
}