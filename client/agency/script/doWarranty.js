const formWarranty = {
    quantiW : document.querySelector("#qttWarranty"),
    batch: document.querySelector("#batchId"),
    service: document.querySelector("#serviceId"),
    submit: document.querySelector("#sendWarranty"),

};

let buttonWarranty = formWarranty.submit.addEventListener("click", (e) => {
    e.preventDefault();
    fetchWarranty()
});


function fetchWarranty() {
    const login = "http://localhost:8080/dealer/recieveWarranty";

    fetch(login, {
        method: "POST",
        headers: {
            Accept: "application/json, text/plain, */*",
            "Content-Type": "application/json",
            "Authorization": "Bearer " + window.sessionStorage.getItem('token'),
            "Username": window.sessionStorage.getItem('username')
        },
        body: JSON.stringify({
            quantity: formWarranty.quantiW.value,
            batchId: formWarranty.batch.value,
            serviceId: formWarranty.service.value,
        }),
    })
        .then(function(response) {
            if (response.ok) {
                loadStore();
                document.getElementById("closeWarranty").click();
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
