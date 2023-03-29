package model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The qnips api models price as an object with "betrag" property. A [Price] is used for modeling to reflect this.
 */

@Serializable
data class Price(@SerialName("Betrag") val amount: Double)

@Serializable
data class Product(
    @SerialName("ProductId") val productId: Long,
    @SerialName("AllergenIds") val allergenIds: List<String>,
    @SerialName("Name") val name: String,
    @SerialName("Price") val price: Price
)