function loadAnalysisTT() {
    this.disabled = true;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState==4){
            if(this.status==200){
                let objArr = JSON.parse(this.responseText);
                document.querySelector("#tsdntt").innerHTML = objArr.nhap + " sản phẩm";
                document.querySelector("#tsdbtt").innerHTML = objArr.xuat + " sản phẩm";
                document.querySelector("#ratett").innerHTML = (((parseInt(objArr.xuat) / parseInt(objArr.nhap)) * 100).toFixed(2)).toString() + " %";
            }
        }
    }
    xhttp.open("GET","http://localhost:8080/dealer/thisMonth",true);
    xhttp.setRequestHeader("Authorization", "Bearer " + window.sessionStorage.getItem('token'));
    xhttp.setRequestHeader("Username", window.sessionStorage.getItem('username'));
    xhttp.send("null");
}

function loadAnalysisTN() {
    this.disabled = true;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState==4){
            if(this.status==200){
                let objArr = JSON.parse(this.responseText);
                document.querySelector("#tsdntn").innerHTML = objArr.nhap + " sản phẩm";
                document.querySelector("#tsdbtn").innerHTML = objArr.xuat + " sản phẩm";
                document.querySelector("#ratetn").innerHTML = (((parseInt(objArr.xuat) / parseInt(objArr.nhap)) * 100).toFixed(2)).toString() + " %";
            }
        }
    }
    xhttp.open("GET","http://localhost:8080/dealer/thisYear",true);
    xhttp.setRequestHeader("Authorization", "Bearer " + window.sessionStorage.getItem('token'));
    xhttp.setRequestHeader("Username", window.sessionStorage.getItem('username'));
    xhttp.send("null");
}

function loadAnalysisTQ() {
    this.disabled = true;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState==4){
            if(this.status==200){
                let objArr = JSON.parse(this.responseText);
                document.querySelector("#tsdntq").innerHTML = objArr.nhap + " sản phẩm";
                document.querySelector("#tsdbtq").innerHTML = objArr.xuat + " sản phẩm";
                document.querySelector("#ratetq").innerHTML = (((parseInt(objArr.xuat) / parseInt(objArr.nhap)) * 100).toFixed(2)).toString() + " %";
            }
        }
    }
    xhttp.open("GET","http://localhost:8080/dealer/thisQuarter",true);
    xhttp.setRequestHeader("Authorization", "Bearer " + window.sessionStorage.getItem('token'));
    xhttp.setRequestHeader("Username", window.sessionStorage.getItem('username'));
    xhttp.send("null");
}
$(document).ready(function(){
    loadAnalysisTT();
    loadAnalysisTN();
    loadAnalysisTQ();

})
