import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun main() {
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
        }
    }.start(wait = true)
}