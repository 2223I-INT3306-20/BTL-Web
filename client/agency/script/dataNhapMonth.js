$(document).ready(function(){
    getListDataMonth();
})
async function getListDataMonth() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const responseListDataMonth = await fetch(
        'http://localhost:8080/dealer/listNhapByMonth', settings);
    const dataListDataMonth = await responseListDataMonth.json();
    document.querySelector("#listNhapMonth tbody").innerHTML = "";
    for(let i = 0; i< dataListDataMonth.length; i++){
        let tr = document.createElement("tr");
        let c1 = document.createElement("td");
        let c2 = document.createElement("td");
        let c3 = document.createElement("td");
        let c4 = document.createElement("td");
        let c5 = document.createElement("td");

        c1.innerHTML = i + 1
        c2.innerHTML = dataListDataMonth[i].name;
        c3.innerHTML = dataListDataMonth[i].sku;
        c4.innerHTML = dataListDataMonth[i].info;
        c5.innerHTML = dataListDataMonth[i].quantity;

        tr.appendChild(c1);
        tr.appendChild(c2);
        tr.appendChild(c3)
        tr.appendChild(c4);
        tr.appendChild(c5);

        document.querySelector("#listNhapMonth tbody").appendChild(tr);
    }

}


