package model

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET

typealias QnipsResponse = Map<String, JsonElement>

interface QnipsService {
    @GET("dbapi/ha")
    fun getProductTable(): Call<QnipsResponse>
}