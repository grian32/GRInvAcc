package data

import kotlinx.serialization.Serializable

@Serializable
data class Sale(
    val itemId: Int,
    val amountSold: Int,
    val pricePerItem: Int
)
