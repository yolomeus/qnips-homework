package model.data

import com.google.gson.annotations.SerializedName

/**
 * Represents an Answer from the qnips api. The mapping from api names to kotlin properties is defined thorugh
 * [SerializedName] annotations and therefore only works with GSON at the moment.
 */
class QnipsResponse(
    @SerializedName("Allergens")
    val allergens: Map<String, Allergen>,
    @SerializedName("Products")
    val products: Map<Long, Product>,
    @SerializedName("Rows")
    val rows: List<Row>
)