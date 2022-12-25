const form = {
    acc: document.querySelector("#acc"),
    password: document.querySelector("#pass"),
    submit: document.querySelector("#submit"),
};
let button = form.submit.addEventListener("click", (e) => {
    e.preventDefault();
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
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
            // code here //
            if (data.error) {
                alert("Error Password or Username"); /*displays error message*/
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
                }
            }
        })
        .catch((err) => {
            console.log(err);
        });
});