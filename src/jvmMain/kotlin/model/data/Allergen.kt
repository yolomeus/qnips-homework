package model.data

import com.google.gson.annotations.SerializedName

data class Allergen(
    @SerializedName("Id") val allergenId: String,
    @SerializedName("Label") val label: String
)