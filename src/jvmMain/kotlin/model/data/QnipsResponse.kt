package model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an Answer from the qnips api. The mapping from api names to kotlin properties is defined thorugh
 * [SerializedName] annotations and therefore only works with GSON at the moment.
 */

@Serializable
class QnipsResponse(
    @SerialName("Allergens")
    val allergens: Map<String, Allergen>,
    @SerialName("Products")
    val products: Map<Long, Product>,
    @SerialName("Rows")
    val rows: List<Row>
)