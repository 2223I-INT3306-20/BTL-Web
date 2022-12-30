function loadStore() {
    this.disabled = true;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState==4){
            if(this.status==200){
                let objArr = JSON.parse(this.responseText);
                document.querySelector("#tbWareHouse tbody").innerHTML = "";
                document.querySelector("#skuPrd").innerHTML = "";

                for(let i = 0; i< objArr.length; i++){
                    let tr = document.createElement("tr");

                    let c2 = document.createElement("td");
                    let c3 = document.createElement("td");
                    let c4 = document.createElement("td");
                    let c5 = document.createElement("td");
                    let c6 = document.createElement("td");
                    let c7 = document.createElement("td")
                    let c8 = document.createElement("td")
                    let o1 = document.createElement("option");
                    o1.setAttribute("value", objArr[i].productId);

                    c2.innerHTML = objArr[i].productId;
                    c3.innerHTML = objArr[i].name;
                    c4.innerHTML = objArr[i].sku;
                    c5.innerHTML = objArr[i].info;
                    c6.innerHTML = objArr[i].slNhap;
                    c7.innerHTML = (objArr[i].giaNhap / objArr[i].slNhap);
                    c8.innerHTML = objArr[i].slXuat;
                    o1.innerHTML = objArr[i].sku + " - Tối đa: " + (objArr[i].slNhap - objArr[i].slXuat) + " - " + (objArr[i].giaNhap / objArr[i].slNhap);

                    tr.appendChild(c2);
                    tr.appendChild(c3);
                    tr.appendChild(c4);
                    tr.appendChild(c5);
                    tr.appendChild(c6);
                    tr.appendChild(c7);
                    tr.appendChild(c8);


                    document.querySelector("#tbWareHouse tbody").appendChild(tr);
                    document.querySelector("#skuPrd").append(o1);
                }
            }
        }
    }
    xhttp.open("GET","http://localhost:8080/dealer/allProductInStore",true);
    xhttp.setRequestHeader("Authorization", "Bearer " + window.sessionStorage.getItem('token'));
    xhttp.setRequestHeader("Username", window.sessionStorage.getItem('username'));
    xhttp.send("null");
}
$(document).ready(function(){
    loadStore();
})
