package model.data

import com.google.gson.annotations.SerializedName

class QnipsResponse(
    @SerializedName("Allergens")
    val allergens: Map<String, Allergen>,
    @SerializedName("Products")
    val products: Map<Long, Product>,
    @SerializedName("Rows")
    val rows: List<Row>
)