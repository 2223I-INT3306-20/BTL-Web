$(document).ready(function(){
    getDataXuatTT();
})
async function getDataXuatTT() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const responseXuatTT = await fetch(
        'http://localhost:8080/factory/xuatByMonth', settings);
    const dataXuatTT = await responseXuatTT.json();

    sizeXuatTT = dataXuatTT.length;

    labelsXuatTT = [];
    valuesXuatTT = [];
    for (i = 0; i < sizeXuatTT; i++) {
        labelsXuatTT.push(dataXuatTT[i].label);
        valuesXuatTT.push(dataXuatTT[i].quantity);
    }


    new Chart(document.getElementById("spdbtt"), {
        type: 'bar',
        data: {
            labels: labelsXuatTT,
            datasets: [
                {
                    label: "Mades",
                    backgroundColor: ["#3e95cd",
                        "#8e5ea2",
                        "#3cba9f",
                        "#e8c3b9",
                        "#c45850",
                        "#CD5C5C",
                        "#40E0D0"],
                    data: valuesXuatTT
                }
            ]
        },
        options: {
            legend: { display: false },
            title: {
                display: true,
                text: 'Product by Year'
            },
            maintainAspectRatio: false
        }
    });

}