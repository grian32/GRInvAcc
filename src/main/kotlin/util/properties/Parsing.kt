package util.properties

import java.io.File

fun parsePropertiesToMap(filepath: String): Map<String, String> {
    val lines = File(filepath).readLines()
    val map = mutableMapOf<String, String>()

    for (line in lines) {
        val (key, value) = line.split("=").map(String::trim)
        map[key] = value
    }

    return map.toMap()
}

fun parseSQLConfig(filepath: String): SQLConfig {
    val map = parsePropertiesToMap(filepath)

    if (!map.keys.containsAll(listOf("databaseURL", "username", "password"))) {
        throw Error("File is not a sql config file.")
    }

    // if code gets here then all those members already exist
    return SQLConfig(
        map["databaseURL"]!!,
        map["username"]!!,
        map["password"]!!
    )
}