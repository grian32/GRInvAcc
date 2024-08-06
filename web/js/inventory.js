let tableBody = document.getElementById("inventory_body")

// <tr>
//                     <td class="border px-3 py-3"> 6 </td>
//                     <td class="border px-3 py-3"> Apples </td>
//                     <td class="border px-3 py-3"> 2000 </td>
//                     <td class="border px-3 py-3"> 4000 </td>
//                     <td class="border px-3 py-3"> 6000 </td>
//                     <td class="border px-3 py-3"> 10000$ </td>
//                 </tr>

addEventListener("DOMContentLoaded", async _ => {
    await populateTable()
})

async function populateTable() {
    let data = await (await fetch("http://localhost:6450/api/item/inventory")).json()

    for (const i of data) {
        tableBody.appendChild(
            makeTableElement(
                i["id"],
                i["itemName"],
                i["currentStock"],
                i["amountSoldMonth"],
                i["amountBoughtMonth"],
                i["profitMonth"]
            )
        )
    }
}

function makeTableElement(itemId, itemName, stock, amountSoldMonth, amountBoughtMonth, profitMonth) {
    let row = document.createElement("tr")

    row.append(
        styledTableCell(itemId),
        styledTableCell(itemName),
        styledTableCell(stock),
        styledTableCell(amountSoldMonth),
        styledTableCell(amountBoughtMonth),
        styledTableCell(profitMonth)
    )

    return row
}

function styledTableCell(content) {
    let cell = document.createElement("td")

    cell.textContent = content
    cell.className = "border px-3 py-3"

    return cell
}
