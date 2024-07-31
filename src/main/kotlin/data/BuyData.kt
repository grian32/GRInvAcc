package data

import kotlinx.serialization.Serializable

@Serializable
data class BuyData(
    val itemId: Int,
    val amountBought: Int,
    val pricePerItem: Int
)
