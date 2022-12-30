function loadValidWarranty() {
    this.disabled = true;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState==4){
            if(this.status==200){
                let objArr = JSON.parse(this.responseText);
                document.querySelector("#tblValidWarranty tbody").innerHTML = "";
                document.querySelector("#batchId").innerHTML = "";
                for(let i = 0; i< objArr.length; i++){
                    let tr = document.createElement("tr");

                    let c1 = document.createElement("td");
                    let c2 = document.createElement("td");
                    let c3 = document.createElement("td");
                    let c4 = document.createElement("td");
                    let c5 = document.createElement("td");
                    let c6 = document.createElement("td");
                    let c7 = document.createElement("td");
                    let c8 = document.createElement("td");
                    let c9 = document.createElement("td");
                    let c10 = document.createElement("td")
                    let o1 = document.createElement("option");

                    o1.innerHTML = objArr[i].batchId + " - " + objArr[i].customerName
                    c1.innerHTML = (i+1);
                    c2.innerHTML = objArr[i].batchId;
                    c3.innerHTML = objArr[i].sku;
                    c4.innerHTML = objArr[i].customerName;
                    c5.innerHTML = objArr[i].customerAddress;
                    c6.innerHTML = objArr[i].customerPhone;
                    c7.innerHTML = objArr[i].soldDate;
                    c8.innerHTML = objArr[i].quantity;
                    c9.innerHTML = objArr[i].warranty;
                    c10.innerHTML += "<td style=\"align-content: center\"><a class=\"btn btn-default\"><em\n" +
                        "                                                        class=\"fa fa-flag-checkered\" aria-hidden=\"true\"></em></a>" +
                        "                                            </td>";

                    c10.setAttribute("data-bs-toggle", "modal");
                    c10.setAttribute("data-bs-target","#doWarranty")
                    c10.setAttribute("onclick", "getValidService()");
                    c10.setAttribute("value", objArr[i].batchId);
                    o1.setAttribute("value", objArr[i].batchId);

                    tr.appendChild(c1)
                    tr.appendChild(c2);
                    tr.appendChild(c3);
                    tr.appendChild(c4);
                    tr.appendChild(c5);
                    tr.appendChild(c6);
                    tr.appendChild(c7);
                    tr.appendChild(c8);
                    tr.appendChild(c9);
                    tr.appendChild(c10);


                    document.querySelector("#tblValidWarranty tbody").appendChild(tr);
                    document.querySelector("#batchId").appendChild(o1);
                }
            }
        }
    }
    xhttp.open("GET","http://localhost:8080/dealer/validWarranty",true);
    xhttp.setRequestHeader("Authorization", "Bearer " + window.sessionStorage.getItem('token'));
    xhttp.setRequestHeader("Username", window.sessionStorage.getItem('username'));
    xhttp.send("null");
}
$(document).ready(function(){
    loadValidWarranty();
})
