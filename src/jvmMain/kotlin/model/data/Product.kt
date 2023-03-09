package model.data

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class Price(@SerializedName("Betrag") val amount: BigDecimal)
data class Product(
    @SerializedName("ProductId") val productId: Long,
    @SerializedName("AllergenIds") val allergenIds: List<String>,
    @SerializedName("Name") val name: String,
    @SerializedName("Price") val price: Price
)