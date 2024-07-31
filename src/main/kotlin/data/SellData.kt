package data

import kotlinx.serialization.Serializable

@Serializable
data class SellData(
    val itemId: Int,
    val amountSold: Int,
    val pricePerItem: Int
)
