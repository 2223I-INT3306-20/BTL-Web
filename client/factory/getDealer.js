function loadDealer () {
    this.disabled = true;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState==4){
            if(this.status==200){
                let objArr = JSON.parse(this.responseText);
                document.querySelector("#toDealer").innerHTML = "";
                for(let i = 0; i< objArr.length; i++){

                    let c0 = document.createElement("option");

                    c0.setAttribute('value', objArr[i].id)

                    c0.innerHTML = objArr[i].locationName;

                    document.querySelector("#toDealer").appendChild(c0);
                }
            }
        }
    }
    xhttp.open("GET","http://localhost:8080/factory/allDealer",true);
    xhttp.setRequestHeader("Authorization", "Bearer " + window.sessionStorage.getItem('token'))
    xhttp.send("null");
}