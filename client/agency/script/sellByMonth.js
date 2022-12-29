$(document).ready(function(){
    getDataSellMonth();
})
async function getDataSellMonth() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const responseChartSellMonth = await fetch(
        'http://localhost:8080/dealer/getSellByMonth', settings);
    const dataSellByMonth = await responseChartSellMonth.json();

    new Chart(document.getElementById("chartSellByMonth"), {
        type: 'bar',
        data: dataSellByMonth,
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