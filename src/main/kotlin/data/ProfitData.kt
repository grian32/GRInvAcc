package data

import kotlinx.serialization.Serializable

@Serializable
data class ProfitData(
    val totalSales: Int,
    val totalBuys: Int,
    val totalProfit: Int
)
