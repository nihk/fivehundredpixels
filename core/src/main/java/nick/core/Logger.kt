package nick.core

interface Logger {
    fun d(message: String)
    fun e(message: String, throwable: Throwable? = null)
}