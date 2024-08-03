
let dropdown = document.getElementById("data_entry_dropdown")
let saleDiv = document.getElementById("sale")
let buyDiv = document.getElementById("buy")
let addItemDiv = document.getElementById("item")
let rejectedDiv = document.getElementById("rejected")

dropdown.onchange = () => {
    let index = dropdown.selectedIndex

    switch (index) {
        case 0:
            noneSelected();
            break;
        case 1:
            addSaleDisplay();
            break;
        case 2:
            addBuyDisplay();
            break;
        case 3:
            addItemDisplay();
            break;
        default:
            break;
    }
}


// kinda dodgy having 4 functions like this & setting display to "" to display the text
// but can't think of a better away around it
function noneSelected() {
    saleDiv.style.setProperty("display", "none")
    buyDiv.style.setProperty("display", "none")
    addItemDiv.style.setProperty("display", "none")
}

function addSaleDisplay() {
    saleDiv.style.setProperty("display", "")
    buyDiv.style.setProperty("display", "none")
    addItemDiv.style.setProperty("display", "none")
}

function addBuyDisplay() {
    saleDiv.style.setProperty("display", "none")
    buyDiv.style.setProperty("display", "")
    addItemDiv.style.setProperty("display", "none")
}

function addItemDisplay() {
    saleDiv.style.setProperty("display", "none")
    buyDiv.style.setProperty("display", "none")
    addItemDiv.style.setProperty("display", "")
}

// TODO: Add error case/display for item not existing
// TODO: maybe abstract this to another function see how that looks
async function submitSale() {
    let itemId = document.getElementById("sell_item_id")
    let itemAmount = document.getElementById("sell_item_amount")
    let itemPPI = document.getElementById("sell_ppi")

    if (
        isNaN(parseInt(itemId.value)) ||
        isNaN(parseInt(itemAmount.value)) ||
        isNaN(parseFloat(itemPPI.value))
    ) {
        rejectedDiv.style.setProperty("display", "")
        return
    }

    let requestBody = JSON.stringify({
            itemId: itemId.value,
            amountSold: itemAmount.value,
            pricePerItem: itemPPI.value
        }
    )

    await fetch("/api/sell", {
        method: "POST",
        body: requestBody,
        headers: {
            "Content-Type": "application/json"
        }
    })

    if (rejectedDiv.style.getPropertyValue("display") !== "none") {
        rejectedDiv.style.setProperty("display", "none")
    }
}


async function submitBuy() {
    let itemId = document.getElementById("buy_item_id")
    let itemAmount = document.getElementById("buy_item_amount")
    let itemPPI = document.getElementById("buy_ppi")

    if (
        isNaN(parseInt(itemId.value)) ||
        isNaN(parseInt(itemAmount.value)) ||
        isNaN(parseFloat(itemPPI.value))
    ) {
        rejectedDiv.style.setProperty("display", "")
        return
    }

    let requestBody = JSON.stringify({
            itemId: itemId.value,
            amountBought: itemAmount.value,
            pricePerItem: itemPPI.value
        }
    )

    await fetch("/api/buy", {
        method: "POST",
        body: requestBody,
        headers: {
            "Content-Type": "application/json"
        }
    })

    if (rejectedDiv.style.getPropertyValue("display") !== "none") {
        rejectedDiv.style.setProperty("display", "none")
    }
}


async function submitItemAdd() {
    let itemName = document.getElementById("name_add_item")
    let important = document.getElementById("important_add_item")

    if (itemName.value === "") {
        rejectedDiv.style.setProperty("display", "")
        return
    }

    let requestBody = JSON.stringify({
            itemName: itemName.value,
            important: important.checked
        }
    )

    await fetch("/api/item", {
        method: "POST",
        body: requestBody,
        headers: {
            "Content-Type": "application/json"
        }
    })

   if (rejectedDiv.style.getPropertyValue("display") !== "none") {
        rejectedDiv.style.setProperty("display", "none")
    }
}