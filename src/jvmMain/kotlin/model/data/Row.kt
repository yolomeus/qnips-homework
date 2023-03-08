package model.data

import com.google.gson.annotations.SerializedName


data class Row(
    @SerializedName("Name") val name: String,
    @SerializedName("Days") val days: List<Day>
)