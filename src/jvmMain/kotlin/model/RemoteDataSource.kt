package model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.data.QnipsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Handles interactions with the qnips [apiService]. Note that the coupling with [QnipsService] is not
 * desirable and could be generalized, however is kept for simplicity of this homework.
 * @property apiData A [StateFlow] of [QnipsResponse] fetched from the [apiService].
 */
class RemoteDataSource(
    private val apiService: QnipsService,
) {

    private val _apiData: MutableStateFlow<QnipsResponse> =
        MutableStateFlow(QnipsResponse(emptyMap(), emptyMap(), emptyList()))

    val apiData: StateFlow<QnipsResponse> = _apiData.asStateFlow()

    /**
     * Triggers asynchronous call to [apiService], in order to fetch a new [QnipsResponse] and update [apiData]
     * accordingly.
     */
    fun updateData() {
        val apiCall = apiService.getProductTable()
        apiCall.enqueue(
            object : Callback<QnipsResponse> {

                override fun onResponse(call: Call<QnipsResponse>, response: Response<QnipsResponse>) {
                    if (response.isSuccessful) {
                        _apiData.value = response.body()!! // assumed not to be null if successful
                    }
                }

                override fun onFailure(call: Call<QnipsResponse>, t: Throwable) {
                    // TODO propagate timeout error to UI, happens...
                    throw t
                }
            })
    }
}
