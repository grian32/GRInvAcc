package util

import me.grian.Database

fun itemIdExists(database: Database, itemId: Int): Boolean {
    val itemIds = database.itemsQueries.selectAll().executeAsList().map { it.id }

    return itemId in itemIds
}