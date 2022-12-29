/* Lấy dữ liệu sản phẩm được bán theo năm */

$(document).ready(function(){
    getDataXuatTN();
})
async function getDataXuatTN() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const responseXuatTN = await fetch(
        'http://localhost:8080/factory/xuatByYear', settings);
    const dataXuatTN = await responseXuatTN.json();

    sizeXuatTN = dataXuatTN.length;

    labelsXuatTN = [];
    valuesXuatTN = [];
    for (i = 0; i < sizeXuatTN; i++) {
        labelsXuatTN.push(dataXuatTN[i].label);
        valuesXuatTN.push(dataXuatTN[i].quantity);
    }


    new Chart(document.getElementById("spdbtn"), {
        type: 'bar',
        data: {
            labels: labelsXuatTN,
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
                    data: valuesXuatTN
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