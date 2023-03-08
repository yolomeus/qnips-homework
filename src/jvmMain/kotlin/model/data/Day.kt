package model.data

import com.google.gson.annotations.SerializedName

data class ProductId(@SerializedName("ProductId") val id: Long)
data class Day(
    @SerializedName("Weekday") val weekday: Int,
    @SerializedName("ProductIds") val productIds: List<ProductId>
)