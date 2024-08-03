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
import me.grian.Database
import me.grian.Items
import org.flywaydb.core.Flyway
import util.properties.parseSQLConfig
import java.io.File

// TODO: use nio.File

fun main() {

    val sqlConfig = parseSQLConfig("sqlConfig.properties")

    // can't get datasource for sql delight after .load() so have to separate
    val flywayPreload = Flyway.configure().dataSource(
        sqlConfig.databaseURL,
        sqlConfig.username,
        sqlConfig.password
    )

    val flyway = flywayPreload.load()
    flyway.migrate()

    val driver: SqlDriver = flywayPreload.dataSource.asJdbcDriver()
    val database = Database(driver)

    embeddedServer(Netty, 6450) {
        install(ContentNegotiation) {
            json()
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

            get("/js/dashboard") {
                call.respondFile(File("web/js/dashboard.js"))
            }

            get("/api/item/important") {
                // converts to ItemData for serialization
                val importantItems = database.itemsQueries.selectAllImportant().executeAsList().map(Items::toItemData)
                call.respondText(Json.encodeToString(importantItems), contentType = ContentType.Application.Json)
            }

            get("/api/profits") {
                val allSales = database.sellQueries.selectAll().executeAsList().sumOf {
                    it.price_per_item * it.amount_sold
                }
                val allBuys = database.buyQueries.selectAll().executeAsList().sumOf {
                    it.price_per_item * it.amount_bought
                }
                val profit = allSales - allBuys

                val profitData = ProfitData(
                    allSales,
                    allBuys,
                    profit
                )

                call.respondText(Json.encodeToString(profitData), contentType = ContentType.Application.Json)
            }

            post("/api/sell") {
                val sale = call.receive<SellData>()
                sale.addToDb(database)
                call.respond(HttpStatusCode.OK)
            }

            post("/api/buy") {
                val buy = call.receive<BuyData>()
                buy.addToDb(database)
                call.respond(HttpStatusCode.OK)
            }

            post("/api/item")  {
                val item = call.receive<ItemData>()
                if (item.currentStock != 0) {
                    call.respond(HttpStatusCode.BadRequest, "Setting stock on item creation is not supported")
                    return@post
                }

                item.addToDb(database)
                call.respond(HttpStatusCode.OK)
            }
        }
    }.start(wait = true)
}