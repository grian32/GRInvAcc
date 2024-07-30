import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.grian.Database
import org.flywaydb.core.Flyway
import util.properties.parseSQLConfig
import java.io.File


fun main() {

    val sqlConfig = parseSQLConfig("sqlConfig.properties")

    val flyway = Flyway.configure().dataSource(
        sqlConfig.databaseURL,
        sqlConfig.username,
        sqlConfig.password
    )

    // TODO: add migration that creates tables
    val driver: SqlDriver = flyway.dataSource.asJdbcDriver()
    val database = Database(driver)

    database.sellQueries.deleteWithID(2)

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