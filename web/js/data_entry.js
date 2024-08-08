let dropdown = document.getElementById("data_entry_dropdown")
let saleDiv = document.getElementById("sell")
let buyDiv = document.getElementById("buy")
let addItemDiv = document.getElementById("item")
let rejectedDiv = document.getElementById("rejected")
let invalidItemIdDiv = document.getElementById("invalid_item_id")
let successfulDiv = document.getElementById("success")

dropdown.onchange = () => {
    let index = dropdown.selectedIndex

    switch (index) {
        case 0:
            setDisplay(null)
            break
        case 1:
            setDisplay(saleDiv)
            break
        case 2:
            setDisplay(buyDiv)
            break
        case 3:
            setDisplay(addItemDiv)
            break
        default:
            break
    }
}

function setDisplay(activeDiv) {
    let divs = [saleDiv, buyDiv, addItemDiv]
    divs.forEach(div => {
        div.style.setProperty("display", div === activeDiv ? "" : "none")
    })
}

async function submitSale() {
    await handleBuySellSubmit("sell", "/api/sell")
}


async function submitBuy() {
    await handleBuySellSubmit("buy", "/api/buy")
}


async function submitItemAdd() {
    let itemName = document.getElementById("name_add_item")
    let important = document.getElementById("important_add_item")

    if (itemName.value === "") {
        showRejected()
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


    showSuccess()
    clearInputs("item")
}

async function handleBuySellSubmit(action, endpoint) {
    let itemId = document.getElementById(`${action}_item_id`)
    let itemAmount = document.getElementById(`${action}_item_amount`)
    let itemPPI = document.getElementById(`${action}_ppi`)

    if (
        isNaN(parseInt(itemId.value)) ||
        isNaN(parseInt(itemAmount.value)) ||
        isNaN(parseFloat(itemPPI.value))
    ) {
        showRejected()
        return
    }

    if (!await itemIdExists(parseInt(itemId.value))) {
        showInvalidItemId()
        return
    }

    let requestBody = {
        itemId: itemId.value,
        pricePerItem: itemPPI.value
    }

    action === "sell" ? requestBody["amountSold"] = itemAmount.value : requestBody["amountBought"] = itemAmount.value

    await fetch(endpoint, {
        method: "POST",
        body: JSON.stringify(requestBody),
        headers: {
            "Content-Type": "application/json"
        }
    })

    showSuccess()
    clearInputs(action)
}

function clearInputs(div) {
    document.getElementById(div).querySelectorAll("input").forEach(elem => {
        elem.value = "";
    });
}

function showRejected() {
    rejectedDiv.style.setProperty("display", "");
    removeInvalidItemId();
    removeSuccess();
}

function showInvalidItemId() {
    invalidItemIdDiv.style.setProperty("display", "");
    removeRejected();
    removeSuccess();
}

function showSuccess() {
    successfulDiv.style.setProperty("display", "");
    removeRejected();
    removeInvalidItemId();
}

function removeRejected() {
    if (rejectedDiv.style.getPropertyValue("display") !== "none") {
        rejectedDiv.style.setProperty("display", "none")
    }
}

function removeInvalidItemId() {
    if (invalidItemIdDiv.style.getPropertyValue("display") !== "none") {
        invalidItemIdDiv.style.setProperty("display", "none")
    }
}

function removeSuccess() {
    if (successfulDiv.style.getPropertyValue("display") !== "none") {
        successfulDiv.style.setProperty("display", "none")
    }
}

async function itemIdExists(itemId) {
    return (await (await fetch("/api/item/ids")).json()).includes(itemId)
}