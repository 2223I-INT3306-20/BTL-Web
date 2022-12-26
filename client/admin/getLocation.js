function loadLocation() {
    this.disabled = true;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState==4){
            if(this.status==200){
                let objArr = JSON.parse(this.responseText);
                document.querySelector("#tblLocation tbody").innerHTML = "";
                for(let i = 0; i< objArr.length; i++){
                    let tr = document.createElement("tr");
                    let c1 = document.createElement("td");
                    let c2 = document.createElement("td");
                    let c3 = document.createElement("td");
                    let c4 = document.createElement("td");
                    let c5 = document.createElement("td");
                    let c6 = document.createElement("td");
                    let c7 = document.createElement("td")

                    c1.innerHTML += "<td align=\"center\"><a class=\"btn btn-default\"><em\n" +
                        "                                                        class=\"fa fa-pencil\"></em></a> <a class=\"btn btn-danger\"><em\n" +
                        "                                                        class=\"fa fa-trash\"></em></a>\n" +
                        "                                            </td>";
                    c2.innerHTML = (i+1);
                    c3.innerHTML = objArr[i].locationName;
                    c4.innerHTML = objArr[i].name;
                    c5.innerHTML = objArr[i].phone ;
                    c6.innerHTML = convertName(objArr[i].locationType);
                    c7.innerHTML = objArr[i].address;

                    tr.appendChild(c1);
                    tr.appendChild(c2);
                    tr.appendChild(c3);
                    tr.appendChild(c4);
                    tr.appendChild(c5);
                    tr.appendChild(c6);
                    tr.appendChild(c7);

                    document.querySelector("#tblLocation tbody").appendChild(tr);
                }
            }
        }
    }
    xhttp.open("GET","http://localhost:8080/admin/getListLocation",true);
    xhttp.setRequestHeader("Authorization", "Bearer " + window.sessionStorage.getItem('token'))
    xhttp.send("null");
}
$(document).ready(function(){
    loadLocation();
})

function convertName(type) {
    if (type === "FACTORY") {
        return "Nhà máy sản xuất";
    } else if (type === "SERVICE") {
        return "Trung tâm bảo hành";
    } else if (type === "DEALER") {
        return "Đại lý ủy quyền"
    }
}