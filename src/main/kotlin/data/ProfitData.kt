package data

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ProfitData(
    val amount: Double,
    val reason: String,
    @Contextual val date: LocalDateTime = LocalDateTime.MIN
)
