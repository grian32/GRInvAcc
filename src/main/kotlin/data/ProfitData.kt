package data

import kotlinx.serialization.Serializable

@Serializable
data class ProfitData(
    val monthlySales: Int,
    val monthlyBuys: Int,
    val monthlyProfit: Int
)
