/* Sản xuất các sản phẩm mới */

const form = {
    sku: document.getElementById("sku"),
    date: document.getElementById("pname"),
    option: document.getElementById("type"),
    quantity: document.getElementById("quantity"),
    submit: document.getElementById("make")
}

let makeProduct = form.submit.addEventListener("click", (e) => {
    e.preventDefault();
    validateMake();
})


function validateMake() {
    var check  = 0;
    var em = document.getElementById("quantity").value;
    if (em <= 0 ) {
        toastShow();
        check = 1;
    }
    if (check === 0) {
        fetchMake();
    }

}

function fetchMake() {
    const login = "http://localhost:8080/factory/makeNew";

    fetch(login, {
        method: "POST",

        headers: {
            Accept: "application/json, text/plain, */*",
            "Content-Type": "application/json",
            "Authorization": "Bearer " + window.sessionStorage.getItem('token'),
            "Username": window.sessionStorage.getItem('username'),
        },
        body: JSON.stringify({
            productSku: form.sku.value,
            productName: "Laptop",
            productMfg: form.date.value,
            optionId: form.option.value,
            quantity: form.quantity.value,
        }),
    })
        .then(function(response) {
            if (response.ok) {
                document.getElementById('closeMake').click();
                loadDoc();
                getData();
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

function toastShow() {
    document.getElementById("liveToast").classList.remove("hide");
    document.getElementById("liveToast").classList.add("show");
    setTimeout(() => {
        document.getElementById("liveToast").classList.remove("show");
        document.getElementById("liveToast").classList.add("hide");
    }, 3000);
}