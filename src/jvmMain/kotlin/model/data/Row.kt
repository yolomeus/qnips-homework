package model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Row(
    @SerialName("Name") val name: String,
    @SerialName("Days") val days: List<Day>
)