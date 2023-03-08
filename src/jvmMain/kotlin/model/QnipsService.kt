package model

import model.data.QnipsResponse
import retrofit2.Call
import retrofit2.http.GET


interface QnipsService {
    @GET("dbapi/ha")
    fun getProductTable(): Call<QnipsResponse>
}