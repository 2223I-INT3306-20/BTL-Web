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

    new Chart(document.getElementById("spdbtn"), {
        type: 'bar',
        data: dataXuatTN,
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