$(document).ready(function(){
    getListDataQuarter();
})
async function getListDataQuarter() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const responseListDataQuarter = await fetch(
        'http://localhost:8080/dealer/listNhapByQuarter', settings);
    const dataListDataQuarter = await responseListDataQuarter.json();
    document.querySelector("#listNhapQuarter tbody").innerHTML = "";
    for(let i = 0; i< dataListDataQuarter.length; i++){
        let tr = document.createElement("tr");
        let c1 = document.createElement("td");
        let c2 = document.createElement("td");
        let c3 = document.createElement("td");
        let c4 = document.createElement("td");
        let c5 = document.createElement("td");

        c1.innerHTML = i + 1
        c2.innerHTML = dataListDataQuarter[i].name;
        c3.innerHTML = dataListDataQuarter[i].sku;
        c4.innerHTML = dataListDataQuarter[i].info;
        c5.innerHTML = dataListDataQuarter[i].quantity;

        tr.appendChild(c1);
        tr.appendChild(c2);
        tr.appendChild(c3)
        tr.appendChild(c4);
        tr.appendChild(c5);

        document.querySelector("#listNhapQuarter tbody").appendChild(tr);
    }

}


