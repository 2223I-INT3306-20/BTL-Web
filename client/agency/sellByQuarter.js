$(document).ready(function(){
    getDataSellQuarter();
})
async function getDataSellQuarter() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const responseChartSellQuarter = await fetch(
        'http://localhost:8080/dealer/getSellByQuarter', settings);
    const dataSellByQuarter = await responseChartSellQuarter.json();

    new Chart(document.getElementById("chartSellByQuarter"), {
        type: 'bar',
        data: dataSellByQuarter,
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