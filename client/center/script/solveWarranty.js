const formWarranty = {
    thamChieu : document.querySelector("#thamChieu"),
    sl: document.querySelector("#sl"),
};

document.querySelector("#cando").addEventListener("click", (e) => {
    e.preventDefault();
    fetchDoneWarranty()
});

document.querySelector("#cannot").addEventListener("click", (e) => {
    e.preventDefault();
    fetchCantWarranty()
});

function fetchDoneWarranty() {
    const login = "http://localhost:8080/service/canWarranty";

    fetch(login, {
        method: "POST",
        headers: {
            Accept: "application/json, text/plain, */*",
            "Content-Type": "application/json",
            "Authorization": "Bearer " + window.sessionStorage.getItem('token'),
            "Username": window.sessionStorage.getItem('username')
        },
        body: JSON.stringify({
            quantity: formWarranty.sl.value,
            productId: formWarranty.thamChieu.value,
            toId: "",
            price: "",
        }),
    })
        .then(function(response) {
            if (response.ok) {
                loadValidWarranty();
                document.getElementById("closeSolve").click();
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

function fetchCantWarranty() {
    const login = "http://localhost:8080/service/cantWarranty";

    fetch(login, {
        method: "POST",
        headers: {
            Accept: "application/json, text/plain, */*",
            "Content-Type": "application/json",
            "Authorization": "Bearer " + window.sessionStorage.getItem('token'),
            "Username": window.sessionStorage.getItem('username')
        },
        body: JSON.stringify({
            quantity: formWarranty.sl.value,
            productId: formWarranty.thamChieu.value,
            toId: "",
            price: "",
        }),
    })
        .then(function(response) {
            if (response.ok) {
                loadValidWarranty();
                document.getElementById("closeSolve").click();
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
