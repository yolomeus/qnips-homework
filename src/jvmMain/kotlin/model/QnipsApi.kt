package model

/**
 * Singleton object for accessing the qnips api using ktor.
 */
object QnipsApi {
    // hardcoded for simplicity + coupling with QnipsService is bad.
    private const val BASE_URL = "https://myprelive.qnips.com/"
    // TODO move ktor code here
}
