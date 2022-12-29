$(document).ready(function(){
    getDataSellYear();
})
async function getDataSellYear() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const responseChartSellYear = await fetch(
        'http://localhost:8080/dealer/getSellByYear', settings);
    const dataSellByYear = await responseChartSellYear.json();

    new Chart(document.getElementById("chartSellByYear"), {
        type: 'bar',
        data: dataSellByYear,
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