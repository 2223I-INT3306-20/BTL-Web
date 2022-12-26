function loadDoc() {
    this.disabled = true;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState==4){
            if(this.status==200){
                let objArr = JSON.parse(this.responseText);
                document.querySelector("#tbProduct tbody").innerHTML = "";
                for(let i = 0; i< objArr.length; i++){
                    let tr = document.createElement("tr");
                    let c1 = document.createElement("td");
                    let c2 = document.createElement("td");
                    let c3 = document.createElement("td");
                    let c4 = document.createElement("td");
                    let c5 = document.createElement("td");
                    let c6 = document.createElement("td");
                    c1.innerHTML = ( i + 1);
                    c2.innerHTML = objArr[i].name;
                    c3.innerHTML = objArr[i].info;
                    c4.innerHTML = objArr[i].slNhap;
                    c5.innerHTML = objArr[i].slXuat;
                    c6.innerHTML = "<input id='xuat' type=\"submit\" value='Xuất'>"

                    tr.appendChild(c1);
                    tr.appendChild(c2);
                    tr.appendChild(c3);
                    tr.appendChild(c4);
                    tr.appendChild(c5);
                    tr.appendChild(c6)

                    document.querySelector("#tbProduct tbody").appendChild(tr);
                }
            }
        }
    }
    xhttp.open("GET","http://localhost:8080/factory/allProduct",true);
    xhttp.setRequestHeader("Authorization", "Bearer " + window.sessionStorage.getItem('token'))
    xhttp.send("null");
}

// $(document).ready(function(){
//     loadDoc();
// // })
// $(document).ready( function () {
//     loadDoc();
//     $('#tbProduct').DataTable();
// } );