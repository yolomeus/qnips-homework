package model

import com.google.gson.JsonElement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RemoteDataSource(
    private val apiService: QnipsService,
) {

    private val _apiData: MutableStateFlow<Map<String, JsonElement>> = MutableStateFlow(emptyMap())
    val apiData: StateFlow<Map<String, JsonElement>> = _apiData.asStateFlow()

    fun updateData() {
        val apiCall = apiService.getProductTable()
        apiCall.enqueue(
            object : Callback<QnipsResponse> {

                override fun onResponse(call: Call<QnipsResponse>, response: Response<QnipsResponse>) {
                    if (response.isSuccessful) {
                        _apiData.value = response.body()!! // assumed not to be null if successful, TODO: double check
                    }
                }

                override fun onFailure(call: Call<QnipsResponse>, t: Throwable) {
                    throw t
                }
            })
    }
}
