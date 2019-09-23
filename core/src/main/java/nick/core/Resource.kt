package nick.core

private interface DataHolder<T> {
    val data: T?
}

sealed class Resource<T>() : DataHolder<T> {
    data class Loading<T>(override val data: T? = null) : Resource<T>()
    data class Success<T>(override val data: T? = null) : Resource<T>()
    data class Error<T>(val throwable: Throwable, override val data: T? = null) : Resource<T>()
}