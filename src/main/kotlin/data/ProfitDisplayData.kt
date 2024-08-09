package data

import kotlinx.serialization.Serializable

@Serializable
data class ProfitDisplayData(
    val monthlySales: Int,
    val monthlyBuys: Int,
    val monthlyProfit: Int,
    val monthlyOtherProfits: Int,
    val monthlyOtherExpenses: Int
)
