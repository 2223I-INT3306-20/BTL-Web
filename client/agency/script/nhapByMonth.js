$(document).ready(function(){
    getDataNhapMonth();
})
async function getDataNhapMonth() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const responseChartNhapMonth = await fetch(
        'http://localhost:8080/dealer/getNhapByMonth', settings);
    const dataNhapByMonth = await responseChartNhapMonth.json();

    new Chart(document.getElementById("chartNhapByMonth"), {
        type: 'bar',
        data: dataNhapByMonth,
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