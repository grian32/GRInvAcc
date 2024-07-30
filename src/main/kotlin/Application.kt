import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import data.Sale
import data.addToDb
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.grian.Database
import org.flywaydb.core.Flyway
import util.properties.parseSQLConfig
import java.io.File


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

            post("/api/sell") {
                val sale = call.receive<Sale>()
                sale.addToDb(database)
                call.respond(HttpStatusCode.OK)
            }
        }
    }.start(wait = true)
}