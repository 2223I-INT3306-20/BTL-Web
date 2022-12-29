$(document).ready(function(){
    getDataNhapYear();
})
async function getDataNhapYear() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const responseChartNhapYear = await fetch(
        'http://localhost:8080/dealer/getNhapByYear', settings);
    const dataNhapByYear = await responseChartNhapYear.json();

    new Chart(document.getElementById("chartNhapByYear"), {
        type: 'bar',
        data: dataNhapByYear,
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