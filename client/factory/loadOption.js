function loadOption() {
    this.disabled = true;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState==4){
            if(this.status==200){
                let objArr = JSON.parse(this.responseText);
                for(let i = 0; i< objArr.length; i++){
                    let o1 = document.createElement("option");

                    o1.innerHTML = ((i + 1) + ": " + objArr[i].optionName + ", " + objArr[i].cpuBrand + " " + objArr[i].cpuName + ", RAM " + (objArr[i].ram).toString() + " GB" + ", ROM " + (objArr[i].rom).toString() + " GB " );

                    document.querySelector("#type").append(o1);
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