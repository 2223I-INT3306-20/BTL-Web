/* Trả lời các yêu cầu nhập hàng từ các đại lý */
const formConfirm = {
    requestId: document.getElementById("requestId"),
}

document.querySelector("#confirmXuat").addEventListener('click',(e) => {
    confirmXuat(2);
});

document.querySelector("#reject").addEventListener('click', (e) => {
    confirmXuat(0);
});
function confirmXuat(code) {
    const login = "http://localhost:8080/factory/confirmTransfer";

    fetch(login, {
        method: "POST",

        headers: {
            Accept: "application/json, text/plain, */*",
            "Content-Type": "application/json",
            "Authorization": "Bearer " + window.sessionStorage.getItem('token'),
            "Username": window.sessionStorage.getItem('username'),
        },
        body: JSON.stringify({
            requestId: formConfirm.requestId.value,
            code: code,

        }),
    })
        .then(function(response) {
            if (response.ok) {
                document.getElementById('closeCf').click();
                loadYCXH()
            }

            return response.json();})
        .then((data) => {
            console.log(data);
            // code here //
            if (data.error) {
                alert("Error"); /*displays error message*/
            } else {
                alert("Oke");

            }
        })
        .catch((err) => {
            console.log(err);
        });
}
