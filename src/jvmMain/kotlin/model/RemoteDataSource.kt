package model

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.data.QnipsResponse

/**
 * Handles interactions with the qnips [apiService]. Note that the coupling with [QnipsService] is not
 * desirable and could be generalized, however is kept for simplicity of this homework.
 * @property apiData A [StateFlow] of [QnipsResponse] fetched from the [apiService].
 */
class RemoteDataSource(
    private val apiService: QnipsApi,
) {

    private val _apiData: MutableStateFlow<QnipsResponse> =
        MutableStateFlow(QnipsResponse(emptyMap(), emptyMap(), emptyList()))

    val apiData: StateFlow<QnipsResponse> = _apiData.asStateFlow()

    /**
     * Triggers asynchronous call to [apiService], in order to fetch a new [QnipsResponse] and update [apiData]
     * accordingly.
     */
    suspend fun updateData() {
        val client = HttpClient()
        val x = client.get("https://myprelive.qnips.com/dbapi/ha")
        val stringBody: String = x.body()
        val response = Json.decodeFromString<QnipsResponse>(stringBody)
        _apiData.value = response
    }
}
