$(document).ready(function(){
    getDataNhapQuarter();
})
async function getDataNhapQuarter() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const responseChartNhapQuarter = await fetch(
        'http://localhost:8080/dealer/getNhapByQuarter', settings);
    const dataNhapByQuarter = await responseChartNhapQuarter.json();

    new Chart(document.getElementById("chartNhapByQuarter"), {
        type: 'bar',
        data: dataNhapByQuarter,
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