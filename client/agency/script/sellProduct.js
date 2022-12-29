const formSellProduct = {
    name: document.querySelector("#cusName"),
    add: document.querySelector("#cusAdd"),
    phone: document.querySelector("#cusPhone"),
    id: document.querySelector("#skuPrd"),
    price: document.querySelector("#priceSell"),
    quantity: document.querySelector("#quantitySell"),
    warranty: document.querySelector("#warranty"),
    submit: document.querySelector("#sellProduct"),
};

let buttonSell = formSellProduct.submit.addEventListener("click", (e) => {
    e.preventDefault();
    validateOption();
});


function fetchSellProduct() {
    const login = "http://localhost:8080/dealer/sellProduct";

    fetch(login, {
        method: "POST",
        headers: {
            Accept: "application/json, text/plain, */*",
            "Content-Type": "application/json",
            "Authorization": "Bearer " + window.sessionStorage.getItem('token'),
            "Username": window.sessionStorage.getItem('username')
        },
        body: JSON.stringify({
            customerName: formSellProduct.name.value,
            customerAddress: formSellProduct.add.value,
            customerPhone: formSellProduct.phone.value,
            productId: formSellProduct.id.value,
            price: formSellProduct.price.value,
            quantity: formSellProduct.quantity.value,
            warranty: formSellProduct.warranty.value,

        }),
    })
        .then(function(response) {
            if (response.ok) {
                loadStore();
                document.getElementById("closeSell").click();
            }
            return response.json();})
        .then((data) => {
            console.log(data);
            if (data.error) {
                alert("Error");
            } else {
                alert("Oke");
            }
        })
        .catch((err) => {
            console.log(err);
        });
}

function validateOption() {
    var check  = 0;


    if (check === 0) {
        fetchSellProduct();
    }
}
