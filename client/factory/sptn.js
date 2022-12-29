/* Lấy dữ liệu sản xuất theo năm của các các cơ sở sản xuất */

$(document).ready(function(){
    getData2();
})
async function getData2() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const response1 = await fetch(
        'http://localhost:8080/factory/makeByYear', settings);
    const data1 = await response1.json();

    new Chart(document.getElementById("myChart2"), {
        type: 'bar',
        data: data1,
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