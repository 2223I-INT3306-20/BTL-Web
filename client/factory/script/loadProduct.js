/* Tải các sản phẩm có trong kho nội bộ */

function loadDoc() {
    this.disabled = true;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState==4){
            if(this.status==200){
                let objArr = JSON.parse(this.responseText);
                document.querySelector("#tbProduct tbody").innerHTML = "";
                document.querySelector("#tfPrd").innerHTML = "";
                var sx = 0;
                var xuat = 0;
                for(let i = 0; i< objArr.length; i++){
                    let tr = document.createElement("tr");
                    let c1 = document.createElement("td");
                    let c2 = document.createElement("td");
                    let c3 = document.createElement("td");
                    let c4 = document.createElement("td");
                    let c5 = document.createElement("td");
                    let c7 = document.createElement("td");
                    let o1 = document.createElement("option");

                    c1.innerHTML = ( i + 1);
                    c2.innerHTML = objArr[i].name;
                    c3.innerHTML = objArr[i].info;
                    c4.innerHTML = objArr[i].slNhap;
                    c5.innerHTML = objArr[i].slXuat;
                    c7.innerHTML = objArr[i].sku;
                    o1.innerHTML = objArr[i].sku;
                    o1.setAttribute("value", objArr[i].productId)

                    tr.appendChild(c1);
                    tr.appendChild(c7)
                    tr.appendChild(c2);
                    tr.appendChild(c3);
                    tr.appendChild(c4);
                    tr.appendChild(c5);

                    sx += parseInt(objArr[i].slNhap);
                    xuat += parseInt(objArr[i].slXuat);

                    document.querySelector("#tbProduct tbody").appendChild(tr);
                    document.querySelector("#tfPrd").append(o1);
                }
                document.querySelector("#daSx").innerHTML = sx + " sản phẩm";
                document.querySelector("#daXuat").innerHTML = xuat + " sản phẩm";
                document.querySelector("#daXuatRate").innerHTML = (((xuat / sx) * 100).toFixed(2)).toString() + " %";
                //document.querySelector("#quantityXuat").setAttribute("max", )
            }
        }
    }
    xhttp.open("GET","http://localhost:8080/factory/allProduct",true);
    xhttp.setRequestHeader("Authorization", "Bearer " + window.sessionStorage.getItem('token'));
    xhttp.setRequestHeader("Username", window.sessionStorage.getItem('username'));
    xhttp.send("null");
}

// $(document).ready(function(){
//     loadDoc();
// // })
// $(document).ready( function () {
//     loadDoc();
//     $('#tbProduct').DataTable();
// } );