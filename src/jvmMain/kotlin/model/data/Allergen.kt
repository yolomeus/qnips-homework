package model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Allergen(
    @SerialName("Id") val allergenId: String,
    @SerialName("Label") val label: String
)