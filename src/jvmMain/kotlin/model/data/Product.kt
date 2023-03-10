package model.data

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

/**
 * The qnips api models price as an object with "betrag" property. A [Price] is used for modeling to reflect this.
 */
data class Price(@SerializedName("Betrag") val amount: BigDecimal)
data class Product(
    @SerializedName("ProductId") val productId: Long,
    @SerializedName("AllergenIds") val allergenIds: List<String>,
    @SerializedName("Name") val name: String,
    @SerializedName("Price") val price: Price
)