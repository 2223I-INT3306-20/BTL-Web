$(document).ready(function(){
    getDataMakeByYear();
})
async function getDataMakeByYear() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const responseDataMakeByYear = await fetch(
        'http://localhost:8080/factory/listMakeByYear', settings);
    const dataMakeByYear = await responseDataMakeByYear.json();
    document.querySelector("#listMakeByYear tbody").innerHTML = "";
    for(let i = 0; i< dataMakeByYear.length; i++){
        let tr = document.createElement("tr");
        let c1 = document.createElement("td");
        let c2 = document.createElement("td");
        let c3 = document.createElement("td");
        let c4 = document.createElement("td");
        let c5 = document.createElement("td");
        //let c6 = document.createElement("td");

        c1.innerHTML = i + 1
        c2.innerHTML = dataMakeByYear[i].name;
        c3.innerHTML = dataMakeByYear[i].sku;
        c4.innerHTML = dataMakeByYear[i].info;
        c5.innerHTML = dataMakeByYear[i].quantity;
        //c6.innerHTML = dataSellMonth[i].price + " đ" ;

        tr.appendChild(c1);
        tr.appendChild(c2);
        tr.appendChild(c3)
        tr.appendChild(c4);
        tr.appendChild(c5);
        //tr.appendChild(c6);

        document.querySelector("#listMakeByYear tbody").appendChild(tr);
    }

}
