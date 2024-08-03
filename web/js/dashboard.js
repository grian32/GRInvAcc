let importantTable = document.getElementById("important_body")
let salesAmount = document.getElementById("total_sales_amount")
let buyAmount = document.getElementById("total_expense_amount")
let profitAmount = document.getElementById("total_profit_amount")

addEventListener("DOMContentLoaded", async _ => {
    await addImportantItems()
    await updateProfits()
})

function makeTableElement(itemName, currentStock) {
    // <tr>
    //     <td className="border px-3 py-3"> Apples</td>
    //     <td className="border px-3 py-3"> 2000</td>
    // </tr>
    let row = document.createElement("tr")
    let nameCell = document.createElement("td")
    let amountCell = document.createElement("td")

    nameCell.textContent = itemName
    nameCell.className = "border px-3 py-3"

    amountCell.textContent = currentStock
    amountCell.className = "border px-3 py-3"

    row.append(nameCell, amountCell)

    return row
}

async function addImportantItems() {
    let importantData = await (await fetch("http://localhost:6450/api/item/important")).json()

    importantData.forEach(i => {
        // kotlinx.serialization doesn't serialize empty values (in this case 0), easier to handle here I think
        i["currentStock"] = i["currentStock"] ? i["currentStock"] : 0
    })

    // sort from highest to lowest stock
    importantData.sort((a, b) => b["currentStock"] - a["currentStock"])

    importantData.forEach(i => {
        importantTable.append(makeTableElement(
            i["itemName"],
            i["currentStock"]
        ))
    })
}

async function updateProfits() {
    let profitsData = await (await fetch("http://localhost:6450/api/profits")).json()

    salesAmount.textContent = profitsData["totalSales"].toString() + "$"
    buyAmount.textContent = profitsData["totalBuys"].toString() + "$"
    profitAmount.textContent = profitsData["totalProfit"].toString() + "$"
}