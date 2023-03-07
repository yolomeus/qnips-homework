package model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    private const val BASE_URL = "https://my.qnips.io/"

    private val retrofit: Retrofit by lazy { buildRetrofit() }
    val service: QnipsService by lazy { retrofit.create(QnipsService::class.java) }


    private fun buildRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
