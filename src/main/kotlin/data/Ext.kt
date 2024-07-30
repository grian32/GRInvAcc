package data

import me.grian.Database

fun Sale.addToDb(database : Database) {
    database.sellQueries.insert(this.itemId, this.amountSold, this.pricePerItem)
}