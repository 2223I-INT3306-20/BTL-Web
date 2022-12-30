/* Yêu cầu nhập hàng */

const formRequets = {
    factory: document.querySelector("#factoryId"),
    product: document.querySelector("#productId"),
    quantity: document.querySelector("#slYcNhap"),
    price: document.querySelector("#giaYcNhap"),
}

document.querySelector("#ycForm").addEventListener('click', (e) => {
    getValidFactory();
})

function getValidFactory() {
    this.disabled = true;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState==4){
            if(this.status==200){
                let objArr = JSON.parse(this.responseText);
                document.querySelector("#factoryId").innerHTML = "";
                for(let i = 0; i< objArr.length; i++){

                    let o1 = document.createElement("option");

                    o1.innerHTML = objArr[i].locationName;
                    o1.setAttribute("value", objArr[i].id);

                    document.querySelector("#factoryId").appendChild(o1);
                }
            }
        }
    }
    xhttp.open("GET","http://localhost:8080/dealer/validFactory",true);
    xhttp.setRequestHeader("Authorization", "Bearer " + window.sessionStorage.getItem('token'))
    xhttp.send("null");
}

function getValidService() {
    this.disabled = true;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState==4){
            if(this.status==200){
                let objArr = JSON.parse(this.responseText);
                document.querySelector("#serviceId").innerHTML = "";
                for(let i = 0; i< objArr.length; i++){

                    let o1 = document.createElement("option");

                    o1.innerHTML = objArr[i].locationName;

                    o1.setAttribute("value", objArr[i].id);

                    document.querySelector("#serviceId").appendChild(o1);
                }
            }
        }
    }
    xhttp.open("GET","http://localhost:8080/dealer/validService",true);
    xhttp.setRequestHeader("Authorization", "Bearer " + window.sessionStorage.getItem('token'))
    xhttp.send("null");
}

function getProductValid() {
    this.disabled = true;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState==4){
            if(this.status==200){
                let objArr = JSON.parse(this.responseText);
                document.querySelector("#productId").innerHTML = "";
                for(let i = 0; i< objArr.length; i++){

                    let o1 = document.createElement("option");

                    o1.innerHTML = objArr[i].name + " - " + objArr[i].sku;
                    o1.setAttribute("value", objArr[i].sku);

                    document.querySelector("#productId").appendChild(o1);
                }
            }
        }
    }
    var url = "http://localhost:8080/dealer/validProduct?id=" + formRequets.factory.value;
    xhttp.open("GET",url,true);
    xhttp.setRequestHeader("Authorization", "Bearer " + window.sessionStorage.getItem('token'))
    xhttp.send(null);
}

function sendRq() {
    const login = "http://localhost:8080/dealer/requestTransfer";

    fetch(login, {
        method: "POST",

        headers: {
            Accept: "application/json, text/plain, */*",
            "Content-Type": "application/json",
            "Authorization": "Bearer " + window.sessionStorage.getItem('token'),
            "Username": window.sessionStorage.getItem('username'),
        },
        body: JSON.stringify({
            productId: document.querySelector("#productId").value,
            quantity: document.querySelector("#slYcNhap").value,
            price: document.querySelector("#giaYcNhap").value,
            factoryId: document.querySelector("#factoryId").value,
        }),
    })
        .then(function(response) {
            if (response.ok) {
                document.getElementById('closeYc').click();
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