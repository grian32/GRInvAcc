package data

import me.grian.Database
import me.grian.Items

// TODO: sell and buy also remove the amount sold/ add the amount bought to the specified item's stock.

fun SellData.addToDb(database : Database) = database.sellQueries.insert(itemId, amountSold, pricePerItem)


fun BuyData.addToDb(database : Database) = database.buyQueries.insert(itemId, amountBought, pricePerItem)


fun ItemData.addToDb(database : Database) = database.itemsQueries.insert(itemName, important)


fun Items.toItemData() = ItemData(current_stock, item_name, important)