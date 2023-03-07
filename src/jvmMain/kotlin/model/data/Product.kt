package model.data

data class Product(
    val productId: String,
    val allergenIds: List<String>,
    val name: String,
    val price: Double
)