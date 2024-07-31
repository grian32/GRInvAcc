
let dropdown = document.getElementById("data_entry_dropdown")
let saleDiv = document.getElementById("sale")
let buyDiv = document.getElementById("buy")
let addItemDiv = document.getElementById("item")

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

async function submitSale() {
    let itemID = document.getElementById("sell_item_id")
    let itemAmount = document.getElementById("sell_item_amount")
    let itemPPI = document.getElementById("sell_ppi")

    // TODO: add validation client side with display on html to tell user it's wrong
    let requestBody = JSON.stringify({
            itemId: itemID.value,
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
}


function submitBuy() {
    // TODO
}


function submitItemAdd() {
    // TODO
}