import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.grian.Database
import me.grian.data.SellQueries
import java.io.File


fun main() {
    val dataSource: HikariDataSource = HikariDataSource()

    // TODO: move to config file

    dataSource.jdbcUrl = "jdbc:postgresql://localhost:5432/grinvacc"
    dataSource.username = "admin"
    dataSource.password = "12345"

    val driver: SqlDriver = dataSource.asJdbcDriver()
    val database = Database(driver)

    embeddedServer(Netty, 8080) {
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