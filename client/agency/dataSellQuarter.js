$(document).ready(function(){
    getDataQuarter();
})
async function getDataQuarter() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const responseDataQuarter = await fetch(
        'http://localhost:8080/dealer/listSellByQuarter', settings);
    const dataSellQuarter = await responseDataQuarter.json();
    document.querySelector("#listSellQuarter tbody").innerHTML = "";
    for(let i = 0; i< dataSellQuarter.length; i++){
        let tr = document.createElement("tr");
        let c1 = document.createElement("td");
        let c2 = document.createElement("td");
        let c3 = document.createElement("td");
        let c4 = document.createElement("td");
        let c5 = document.createElement("td");
        let c6 = document.createElement("td");

        c1.innerHTML = i + 1
        c2.innerHTML = dataSellQuarter[i].name;
        c3.innerHTML = dataSellQuarter[i].sku;
        c4.innerHTML = dataSellQuarter[i].info;
        c5.innerHTML = dataSellQuarter[i].quantity;
        c6.innerHTML = dataSellQuarter[i].price + " đ" ;

        tr.appendChild(c1);
        tr.appendChild(c2);
        tr.appendChild(c3)
        tr.appendChild(c4);
        tr.appendChild(c5);
        tr.appendChild(c6);

        document.querySelector("#listSellQuarter tbody").appendChild(tr);
    }

}


