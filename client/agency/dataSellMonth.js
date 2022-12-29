$(document).ready(function(){
    getDataMonth();
})
async function getDataMonth() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const responseDataMonth = await fetch(
        'http://localhost:8080/dealer/listSellByMonth', settings);
    const dataSellMonth = await responseDataMonth.json();
    document.querySelector("#listSellMonth tbody").innerHTML = "";
    for(let i = 0; i< dataSellMonth.length; i++){
        let tr = document.createElement("tr");
        let c1 = document.createElement("td");
        let c2 = document.createElement("td");
        let c3 = document.createElement("td");
        let c4 = document.createElement("td");
        let c5 = document.createElement("td");
        let c6 = document.createElement("td");

        c1.innerHTML = i + 1
        c2.innerHTML = dataSellMonth[i].name;
        c3.innerHTML = dataSellMonth[i].sku;
        c4.innerHTML = dataSellMonth[i].info;
        c5.innerHTML = dataSellMonth[i].quantity;
        c6.innerHTML = dataSellMonth[i].price + " đ" ;

        tr.appendChild(c1);
        tr.appendChild(c2);
        tr.appendChild(c3)
        tr.appendChild(c4);
        tr.appendChild(c5);
        tr.appendChild(c6);

        document.querySelector("#listSellMonth tbody").appendChild(tr);
    }

}


