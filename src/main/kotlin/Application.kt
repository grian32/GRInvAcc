import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import data.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.serializersModuleOf
import me.grian.Buy
import me.grian.Database
import me.grian.Items
import me.grian.Sell
import org.flywaydb.core.Flyway
import util.LocalDateTimeSerializer
import util.itemIdExists
import util.properties.parseSQLConfig
import java.io.File
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.math.roundToInt

fun main() {

    val sqlConfig = parseSQLConfig("sqlConfig.properties")

    // can't get datasource for sql delight after .load() so have to separate
    val flywayPreload = Flyway.configure().dataSource(
        sqlConfig.databaseURL,
        sqlConfig.username,
        sqlConfig.password
    )

    // TODO: add users bought from/sold to to buy/sell
    // TODO: add expense/profit to data entry tab
    // TODO: add users to data entry tab
    // TODO: add buy/sells tab that lets you filter by the following: user, date, item id, all in combo

    val flyway = flywayPreload.load()
    flyway.migrate()

    val driver: SqlDriver = flywayPreload.dataSource.asJdbcDriver()
    val database = Database(driver)

    val dateSerializer = serializersModuleOf(LocalDateTimeSerializer)

    embeddedServer(Netty, 6450) {
        install(ContentNegotiation) {
            json( Json {
                serializersModule = dateSerializer
            } )
        }

        routing {
            get("/") {
                call.respondFile(File("web/index.html"))
            }

            get("/data_entry") {
                call.respondFile(File("web/pages/data_entry.html"))
            }

            get("/inventory") {
                call.respondFile(File("web/pages/inventory.html"))
            }

            get("/js/data_entry") {
                call.respondFile(File("web/js/data_entry.js"))
            }

            get("/js/inventory") {
                call.respondFile(File("web/js/inventory.js"))
            }

            get("/js/dashboard") {
                call.respondFile(File("web/js/dashboard.js"))
            }

            get("/api/item/important") {
                // converts to ItemData for serialization
                val importantItems = database.itemsQueries.selectAllImportant().executeAsList().map(Items::toItemData)
                call.respondText(Json.encodeToString(importantItems), contentType = ContentType.Application.Json)
            }

            get("/api/profits") {
                val currentMonth = LocalDateTime.now(ZoneOffset.UTC).month

                val monthlySales = database.sellQueries.selectAll().executeAsList().map(Sell::toSellData).filter {
                    it.date.month == currentMonth
                }.sumOf {
                    it.pricePerItem * it.amountSold
                }.roundToInt()

                val monthlyBuys = database.buyQueries.selectAll().executeAsList().map(Buy::toBuyData).filter {
                    it.date.month == currentMonth
                }.sumOf {
                    it.pricePerItem * it.amountBought
                }.roundToInt()

                val profit = monthlySales - monthlyBuys

                val profitData = ProfitData(
                    monthlySales,
                    monthlyBuys,
                    profit
                )

                call.respondText(Json.encodeToString(profitData), contentType = ContentType.Application.Json)
            }

            get("api/item/inventory") {
                val items = database.itemsQueries.selectAll().executeAsList().map(Items::toItemData)
                val sells = database.sellQueries.selectAll().executeAsList().map(Sell::toSellData)
                val buys = database.buyQueries.selectAll().executeAsList().map(Buy::toBuyData)

                val currentMonth = LocalDateTime.now(ZoneOffset.UTC).month

                val inventoryData: MutableList<InventoryItemData> = mutableListOf()

                items.forEach { item ->
                    val soldCurrentMonth = sells.filter { it.itemId == item.id && it.date.month == currentMonth }
                    val boughtCurrentMonth = buys.filter { it.itemId == item.id && it.date.month == currentMonth }

                    val amountSold = soldCurrentMonth.sumOf { it.amountSold }
                    val amountBought = boughtCurrentMonth.sumOf { it.amountBought }
                    val profit = soldCurrentMonth.sumOf { it.pricePerItem * it.amountSold } - boughtCurrentMonth.sumOf { it.pricePerItem * it.amountBought }

                    inventoryData.add(InventoryItemData(
                        item.id,
                        item.currentStock,
                        item.itemName,
                        item.important,
                        amountSold,
                        amountBought,
                        profit.roundToInt()
                    ))
                }

                inventoryData.sortBy { it.id }

                call.respondText(Json.encodeToString(inventoryData), contentType = ContentType.Application.Json)
            }

            get("api/item/ids") {
                val ids = database.itemsQueries.selectAll().executeAsList().map { it.id }
                call.respondText(Json.encodeToString(ids), contentType = ContentType.Application.Json)
            }

            post("/api/sell") {
                val sale = call.receive<SellData>()

                if (!itemIdExists(database, sale.itemId)) {
                    call.respond(HttpStatusCode.UnprocessableEntity, "Item Id does not exist")
                    return@post
                }

                sale.addToDb(database)
                call.respond(HttpStatusCode.OK)
            }

            post("/api/buy") {
                val buy = call.receive<BuyData>()

                if (!itemIdExists(database, buy.itemId)) {
                    call.respond(HttpStatusCode.UnprocessableEntity, "Item Id does not exist")
                    return@post
                }

                buy.addToDb(database)
                call.respond(HttpStatusCode.OK)
            }

            post("/api/item") {
                val item = call.receive<ItemData>()
                if (item.currentStock != 0) {
                    call.respond(HttpStatusCode.BadRequest, "Setting stock on item creation is not supported")
                    return@post
                }

                if (item.id != 0) {
                    call.respond(HttpStatusCode.BadRequest, "Cannot set item id on creation")
                    return@post
                }


                item.addToDb(database)
                call.respond(HttpStatusCode.OK)
            }

        }
    }.start(wait = true)
}