$(document).ready(function(){
    getListDataYear();
})
async function getListDataYear() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const responseListDataYear = await fetch(
        'http://localhost:8080/dealer/listNhapByYear', settings);
    const dataListDataYear = await responseListDataYear.json();
    document.querySelector("#listNhapYear tbody").innerHTML = "";
    for(let i = 0; i< dataListDataYear.length; i++){
        let tr = document.createElement("tr");
        let c1 = document.createElement("td");
        let c2 = document.createElement("td");
        let c3 = document.createElement("td");
        let c4 = document.createElement("td");
        let c5 = document.createElement("td");

        c1.innerHTML = i + 1
        c2.innerHTML = dataListDataYear[i].name;
        c3.innerHTML = dataListDataYear[i].sku;
        c4.innerHTML = dataListDataYear[i].info;
        c5.innerHTML = dataListDataYear[i].quantity;

        tr.appendChild(c1);
        tr.appendChild(c2);
        tr.appendChild(c3)
        tr.appendChild(c4);
        tr.appendChild(c5);

        document.querySelector("#listNhapYear tbody").appendChild(tr);
    }

}


