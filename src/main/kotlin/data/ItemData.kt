package data

import kotlinx.serialization.Serializable

@Serializable
data class ItemData(
    val id: Int,
    val currentStock: Int = 0,
    val itemName: String,
    val important: Boolean
)
