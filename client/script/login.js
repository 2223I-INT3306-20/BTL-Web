const form = {
    acc: document.querySelector("#acc"),
    password: document.querySelector("#pass"),
    submit: document.querySelector("#submit"),
};
let button = form.submit.addEventListener("click", (e) => {
    e.preventDefault();
    const endpoint = "http://localhost:8080/api/auth/login";

    fetch(endpoint, {
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
                window.open(
                    "./admin/admin.html", "_self");
            }
        })
        .catch((err) => {
            console.log(err);
        });
});