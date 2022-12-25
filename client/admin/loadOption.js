function loadOption() {
    this.disabled = true;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState==4){
            if(this.status==200){
                let objArr = JSON.parse(this.responseText);
                document.querySelector("#tblOption tbody").innerHTML = "";
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
                    let o1 = document.createElement("option");

                    c1.innerHTML = objArr[i].optionId;
                    c2.innerHTML = objArr[i].brandName;
                    c3.innerHTML = objArr[i].optionName;
                    c4.innerHTML = (objArr[i].screenSize).toString() + " inch " + objArr[i].resolution + " " + objArr[i].screenType ;
                    c5.innerHTML = (objArr[i].battery).toString();
                    c6.innerHTML = objArr[i].cpuBrand + " " + objArr[i].cpuName;
                    c7.innerHTML = (objArr[i].ram).toString() + " GB";
                    c8.innerHTML = (objArr[i].rom).toString() + " GB " + objArr[i].romType;
                    c9.innerHTML = objArr[i].gpu;
                    o1.innerHTML = ((i + 1) + ": " + objArr[i].optionName + ", " + objArr[i].cpuBrand + " " + objArr[i].cpuName + ", RAM " + (objArr[i].ram).toString() + " GB" + ", ROM " + (objArr[i].rom).toString() + " GB " );
                    tr.appendChild(c1);
                    tr.appendChild(c2);
                    tr.appendChild(c3);
                    tr.appendChild(c4);
                    tr.appendChild(c5);
                    tr.appendChild(c6);
                    tr.appendChild(c7);
                    tr.appendChild(c8);
                    tr.appendChild(c9);
                    document.querySelector("#tblOption tbody").appendChild(tr);
                    document.querySelector("#selectOption").append(o1);
                }
            }
        }
    }
    xhttp.open("GET","http://localhost:8080/factory/allOption",true);
    xhttp.setRequestHeader("Authorization", "Bearer " + window.sessionStorage.getItem('token'))
    xhttp.send("null");
}
$(document).ready(function(){
    loadOption();
})