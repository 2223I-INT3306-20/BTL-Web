/* Tải các yêu cầu xuất hàng từ các Đại lý cho các Cơ sở sản xuất */

$(document).ready(function(){
    loadYCXH();
});
function loadYCXH() {
    this.disabled = true;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState==4){
            if(this.status==200){
                let objArr = JSON.parse(this.responseText);
                document.querySelector("#tblYcxh tbody").innerHTML = "";
                document.querySelector("#requestId").innerHTML = "";
                for(let i = 0; i< objArr.length; i++){

                    let tr = document.createElement("tr");
                    let c1 = document.createElement("td");
                    let c2 = document.createElement("td");
                    let c3 = document.createElement("td");
                    let c4 = document.createElement("td");
                    let c5 = document.createElement("td");
                    let c6 = document.createElement("td");
                    let o1 = document.createElement("option");
                    o1.setAttribute("value", objArr[i].requestId);

                    c1.innerHTML = ( i + 1);
                    c2.innerHTML = objArr[i].requestId;
                    c3.innerHTML = objArr[i].productId;
                    c4.innerHTML = objArr[i].dealerId;
                    c5.innerHTML = objArr[i].quantity;
                    c6.innerHTML = objArr[i].price;
                    o1.innerHTML = objArr[i].requestId + " - SL: " + objArr[i].quantity + ", price: " + objArr[i].price;


                    tr.appendChild(c1);
                    tr.appendChild(c2);
                    tr.appendChild(c3);
                    tr.appendChild(c4);
                    tr.appendChild(c5);
                    tr.appendChild(c6);

                    document.querySelector("#requestId").appendChild(o1);
                    document.querySelector("#tblYcxh tbody").appendChild(tr);
                }
            }
        }
    }
    xhttp.open("GET","http://localhost:8080/factory/getListRequest",true);
    xhttp.setRequestHeader("Authorization", "Bearer " + window.sessionStorage.getItem('token'));
    xhttp.setRequestHeader("Username", window.sessionStorage.getItem('username'));
    xhttp.send("null");
}

window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple10 = document.getElementById('tblYcxh');
    if (datatablesSimple10) {
        new simpleDatatables.DataTable(datatablesSimple10);
    }
});

