package data

import kotlinx.serialization.Serializable

@Serializable
data class InventoryItemData(
    val id: Int,
    val currentStock: Int,
    val itemName: String,
    // not currently used on front-end, sending as may be used in future
    val important: Boolean,
    val amountSoldMonth: Int,
    val amountBoughtMonth: Int,
    val profitMonth: Int
)
