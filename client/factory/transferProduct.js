const formTransfer = {
    id : document.getElementById("tfPrd"),
    dealer: document.getElementById("toDealer"),
    quantity: document.getElementById("quantityXuat"),
    submit: document.getElementById("xuat"),
}

let xuat = document.querySelector("#xuat").addEventListener('click', (e) => {
    e.preventDefault();
    fetchTransfer();
})

function fetchTransfer() {
    const login = "http://localhost:8080/factory/toDealer";

    fetch(login, {
        method: "POST",

        headers: {
            Accept: "application/json, text/plain, */*",
            "Content-Type": "application/json",
            "Authorization": "Bearer " + window.sessionStorage.getItem('token'),
            "Username": window.sessionStorage.getItem('username'),
        },
        body: JSON.stringify({
            productId: formTransfer.id.value,
            toId: formTransfer.dealer.value,
            quantity: formTransfer.quantity.value,
        }),
    })
        .then(function(response) {
            if (response.ok) {
                loadDoc();
                document.getElementById("closeXuat").click();
            }

            return response.json();})
        .then((data) => {
            console.log(data);
            // code here //
            if (data.error) {
                alert("Error Password or Username"); /*displays error message*/
            } else {
                alert("Oke");

            }
        })
        .catch((err) => {
            console.log(err);
        });
}

