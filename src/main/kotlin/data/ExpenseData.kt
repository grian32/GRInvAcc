package data

import kotlinx.serialization.Serializable

@Serializable
data class ExpenseData(
    val amount: Int,
    val reason: String
)
