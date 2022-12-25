const form = {
    acc: document.querySelector("#inputUsername"),
    password: document.querySelector("#inputPassword"),
    submit: document.querySelector("#submit"),
};


let button = form.submit.addEventListener("click", (e) => {
    e.preventDefault();
    validateForm()
});

function validateForm() {

    if (form.acc.value === "") {
        document.getElementById("acc").classList.add("error");
    }

    if (form.password.value === "") {
        document.getElementById("pass").classList.add("error");
    }

    if (form.acc.value !== "" && form.password.value !== "") {
        fetchLogin();
    } else {
        //alert("Nhapj loi roi")
    }
}

function fetchLogin() {
    const login = "http://localhost:8080/auth/login";

    fetch(login, {
        method: "POST",

        headers: {
            Accept: "application/json, text/plain, */*",
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            username: form.acc.value,
            password: form.password.value,
        }),
    })
        .then(function(response) {
            if (!response.ok) {
                toastShow();
            }
            return response.json();})
        .then((data) => {
            console.log(data);
            if (data.error) {
                alert("Error Password or Username");
            } else {
                var tokenStr = data.accessToken;
                var name = data.name;
                var role = data.role;
                console.log(tokenStr)
                window.sessionStorage.setItem('token', tokenStr);
                window.sessionStorage.setItem('name', name)
                if (role === "ROLE_ADMIN") {
                    window.open(
                        "./admin/admin.html", "_self");
                } else if (role === "ROLE_FACTORY") {
                    window.open("./factory/factory.html", "_self");
                } else if (role === "ROLE_DEALER") {
                    window.open("./agency/agency.html", "_self");
                } else if (role === "ROLE_SERVICE") {
                    window.open("./center/center.html", "_self");
                }
            }
        })
        .catch((err) => {
            console.log(err);
        });
}

document.getElementById("inputUsername").onfocus = function () {
    if (document.getElementById("acc").classList.contains("error")) {
        document.getElementById("acc").classList.remove("error");
    }
}

document.getElementById("inputPassword").onfocus = function () {
    if (document.getElementById("pass").classList.contains("error")) {
        document.getElementById("pass").classList.remove("error");
    }
}

function toastShow() {
    document.getElementById("liveToast").classList.remove("hide");
    document.getElementById("liveToast").classList.add("show");
    setTimeout(() => {  
        document.getElementById("liveToast").classList.remove("show");
        document.getElementById("liveToast").classList.add("hide");
    }, 4000);
}