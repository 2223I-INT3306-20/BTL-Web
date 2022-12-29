/* Lấy dữ liệu sản phẩm đã bán theo tháng */

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

    new Chart(document.getElementById("spdbtt"), {
        type: 'bar',
        data: dataXuatTT,
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