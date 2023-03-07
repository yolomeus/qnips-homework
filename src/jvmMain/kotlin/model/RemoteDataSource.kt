package model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RemoteDataSource(
    private val apiService: QnipsService,
) {

    var apiData: MutableState<Map<String, JsonElement>> = mutableStateOf(emptyMap())

    init {
        updateData()
    }

    fun updateData() {
        val apiCall = apiService.getProductTable()
        apiCall.enqueue(
            object : Callback<QnipsResponse> {

                override fun onResponse(call: Call<QnipsResponse>, response: Response<QnipsResponse>) {
                    if (response.isSuccessful) {
                        apiData.value = response.body()!! // should not be null if successful
                    }
                }

                override fun onFailure(call: Call<QnipsResponse>, t: Throwable) {
                    throw t
                }
            })
    }
}
