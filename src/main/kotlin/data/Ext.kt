package data

import me.grian.Database

fun SellData.addToDb(database : Database) {
    database.sellQueries.insert(itemId, amountSold, pricePerItem)
}

fun BuyData.addToDb(database : Database) {
    database.buyQueries.insert(itemId, amountBought, pricePerItem)
}

fun ItemData.addToDb(database : Database) {
    database.itemsQueries.insert(itemName, important)
}