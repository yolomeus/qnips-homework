package model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Like [Price], product ids are modeled as object with id property.
 */

@Serializable
data class ProductId(@SerialName("ProductId") val id: Long)

@Serializable
data class Day(
    @SerialName("Weekday") val weekday: Int,
    @SerialName("ProductIds") val productIds: List<ProductId>
)