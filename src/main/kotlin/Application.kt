import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.grian.Database
import me.grian.data.SellQueries
import util.properties.parseSQLConfig
import java.io.File


fun main() {
    val dataSource = HikariDataSource()

    val configFile = parseSQLConfig("sqlConfig.properties")

    dataSource.jdbcUrl = configFile.databaseURL
    dataSource.username = configFile.username
    dataSource.password = configFile.password

    // TODO: add migration that creates tables
    val driver: SqlDriver = dataSource.asJdbcDriver()
    val database = Database(driver)

    embeddedServer(Netty, 6450) {
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
        }
    }.start(wait = true)
}