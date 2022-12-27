const form = {
    name: document.querySelector("#ten"),
    acc: document.querySelector("#username"),
    password: document.querySelector("#pw"),
    role: document.querySelector("#role"),
    submit: document.querySelector("#btnAddAcc"),
};

let button = form.submit.addEventListener("click", (e) => {
    e.preventDefault();
    validateAccount();
});


function fetchAddAcount() {
    const login = "http://localhost:8080/auth/signup";

    fetch(login, {
        method: "POST",
        headers: {
            Accept: "application/json, text/plain, */*",
            "Content-Type": "application/json",
            "Authorization": "Bearer " + window.sessionStorage.getItem('token'),
        },
        body: JSON.stringify({
            name: form.name.value,
            username: form.acc.value,
            password: form.password.value,
            locationId: form.role.value,
        }),
    })
        .then(function(response) {
            if (!response.ok) {
                //toastShow();
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

function validateAccount() {
    var check  = 0;
    var ten = document.getElementById("ten").value;
    if (ten.trim().length === 0) {
        document.getElementById("error_ten").innerHTML = "Quý vị chưa nhập họ tên";
        document.getElementById("error_ten").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_ten").innerHTML = "";
        document.getElementById("error_ten").classList.remove("error");
    }

    var em = document.getElementById("username").value;
    if (em.trim().length === 0) {
        document.getElementById("error_em").innerHTML = "Quý vị chưa nhập email";
        document.getElementById("error_em").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_em").innerHTML = "";
        document.getElementById("error_em").classList.remove("error");
    }

    var pw = document.getElementById("pw").value;
    if (pw.trim().length === 0) {
        document.getElementById("error_pw").innerHTML = "Quý vị chưa nhập mật khẩu";
        document.getElementById("error_pw").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_pw").innerHTML = "";
        document.getElementById("error_pw").classList.remove("error");
    }

    if (check === 0 ) {
        fetchAddAcount();
    } 
}