const formAddLocation = {
    name: document.querySelector("#locationName"),
    manage: document.querySelector("#locationManage"),
    phone: document.querySelector("#phone"),
    address: document.querySelector("#address"),
    type: document.querySelector("#locationType"),
    submit: document.querySelector("#btnAddLocation"),
};

let buttonLocation = formAddLocation.submit.addEventListener("click", (e) => {
    e.preventDefault();
    validateLocation();
});


function fetchAddLocation() {
    const login = "http://localhost:8080/admin/addLocation";

    fetch(login, {
        method: "POST",
        headers: {
            Accept: "application/json, text/plain, */*",
            "Content-Type": "application/json",
            "Authorization": "Bearer " + window.sessionStorage.getItem('token'),
        },
        body: JSON.stringify({
            locationType: formAddLocation.type.value,
            name: formAddLocation.manage.value,
            locationName: formAddLocation.name.value,
            address: formAddLocation.address.value,
            phone: formAddLocation.phone.value.toString(),
        }),
    })
        .then(function(response) {
            if (!response.ok) {
                alert("Error!")
            }
            loadLocation();
            return response.json();})
        .then((data) => {
            console.log(data);
            if (data.error) {
                alert("Error " + data.error);
            } else {
                alert("Oke");
            }
        })
        .catch((err) => {
            console.log(err);
        });
}

function validateLocation() {
    var check  = 0;
    var ten = document.getElementById("locationName").value;
    if (ten.trim().length === 0) {
        document.getElementById("error_name").innerHTML = "Chưa nhập tên cơ sở";
        document.getElementById("error_name").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_name").innerHTML = "";
        document.getElementById("error_name").classList.remove("error");
    }

    var em = document.getElementById("locationManage").value;
    if (em.trim().length === 0) {
        document.getElementById("error_manage").innerHTML = "Chưa nhập tên";
        document.getElementById("error_manage").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_manage").innerHTML = "";
        document.getElementById("error_manage").classList.remove("error");
    }

    var pw = document.getElementById("phone").value;
    if (pw.trim().length === 0) {
        document.getElementById("error_phone").innerHTML = "Chưa nhập số điện thoại";
        document.getElementById("error_phone").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_phone").innerHTML = "";
        document.getElementById("error_phone").classList.remove("error");
    }

    var pw = document.getElementById("address").value;
    if (pw.trim().length === 0) {
        document.getElementById("error_add").innerHTML = "Chưa nhập địa chỉ";
        document.getElementById("error_add").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_add").innerHTML = "";
        document.getElementById("error_add").classList.remove("error");
    }

    if (check === 0 ) {
        fetchAddLocation();
    }
}
