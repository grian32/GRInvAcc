package data

import me.grian.Database
import me.grian.Items

fun SellData.addToDb(database : Database) {
    database.sellQueries.insert(itemId, amountSold, pricePerItem)
    val stockBeforeSell = database.itemsQueries.selectWithId(itemId).executeAsOne().current_stock
    database.itemsQueries.updateStock(stockBeforeSell - amountSold, itemId)
}


fun BuyData.addToDb(database : Database) {
    database.buyQueries.insert(itemId, amountBought, pricePerItem)
    val stockBeforeBuy = database.itemsQueries.selectWithId(itemId).executeAsOne().current_stock
    database.itemsQueries.updateStock(stockBeforeBuy + amountBought, itemId)
}


fun ItemData.addToDb(database : Database) = database.itemsQueries.insert(itemName, important)


fun Items.toItemData() = ItemData(id, current_stock, item_name, important)