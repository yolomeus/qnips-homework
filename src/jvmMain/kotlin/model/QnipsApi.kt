package model

import model.QnipsApi.service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton object for accessing the qnips api using [Retrofit].
 * @property service The [QnipsService] providing method for api calls.
 */
object QnipsApi {
    // hardcoded for simplicity + coupling with QnipsService is bad.
    private const val BASE_URL = "https://myprelive.qnips.com/"

    private val retrofit: Retrofit by lazy { buildRetrofit() }
    val service: QnipsService by lazy { retrofit.create(QnipsService::class.java) }

    /**
     * Configure and build the retrofit service.
     */
    private fun buildRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
