/* Lấy dữ liệu sản phẩm được sản xuất theo các tháng */

$(document).ready(function(){
    getchartMakeTT();
})
async function getchartMakeTT() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const response = await fetch(
        'http://localhost:8080/factory/makeByMonth', settings);
    const datachartXuatTT = await response.json();

    new Chart(document.getElementById("chartXuatTT"), {
        type: 'bar',
        data: datachartXuatTT,
        options: {
            legend: { display: false },
            title: {
                display: true,
                text: 'Product by Month'
            },
            maintainAspectRatio: false
        }
    });

}