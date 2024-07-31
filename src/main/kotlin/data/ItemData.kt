package data

import kotlinx.serialization.Serializable

@Serializable
data class ItemData(
    val currentStock: Int = 0,
    val itemName: String,
    val important: Boolean
)
