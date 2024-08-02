let importantTable = document.getElementById("important_body")

addEventListener("DOMContentLoaded", async _ => {
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