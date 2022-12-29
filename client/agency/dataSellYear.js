$(document).ready(function(){
    getDataYear();
})
async function getDataYear() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const responseDataYear = await fetch(
        'http://localhost:8080/dealer/listSellByYear', settings);
    const dataSellYear = await responseDataYear.json();
    document.querySelector("#listSellYear tbody").innerHTML = "";
    for(let i = 0; i< dataSellYear.length; i++){
        let tr = document.createElement("tr");
        let c1 = document.createElement("td");
        let c2 = document.createElement("td");
        let c3 = document.createElement("td");
        let c4 = document.createElement("td");
        let c5 = document.createElement("td");
        let c6 = document.createElement("td");

        c1.innerHTML = i + 1
        c2.innerHTML = dataSellYear[i].name;
        c3.innerHTML = dataSellYear[i].sku;
        c4.innerHTML = dataSellYear[i].info;
        c5.innerHTML = dataSellYear[i].quantity;
        c6.innerHTML = dataSellYear[i].price + " đ" ;

        tr.appendChild(c1);
        tr.appendChild(c2);
        tr.appendChild(c3)
        tr.appendChild(c4);
        tr.appendChild(c5);
        tr.appendChild(c6);

        document.querySelector("#listSellYear tbody").appendChild(tr);
    }

}


