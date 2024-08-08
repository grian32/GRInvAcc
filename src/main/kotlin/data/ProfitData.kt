package data

import kotlinx.serialization.Serializable

@Serializable
data class ProfitData(
    val amount: Int,
    val reason: String
)
