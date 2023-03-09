package model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import model.data.QnipsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RemoteDataSource(
    private val apiService: QnipsService,
) {

    private val _apiData: MutableStateFlow<QnipsResponse> =
        MutableStateFlow(QnipsResponse(emptyMap(), emptyMap(), emptyList()))

    private val apiData: StateFlow<QnipsResponse> = _apiData.asStateFlow()

    val rows = apiData.map { it.rows }
    val products = apiData.map { it.products }
    val allergens = apiData.map { it.allergens }


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
