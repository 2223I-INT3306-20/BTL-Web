$(document).ready(function(){
    getDatachartMakeByQuarter();
})
async function getDatachartMakeByQuarter() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const responsechartMakeByQuarter = await fetch(
        'http://localhost:8080/factory/makeByQuarter', settings);
    const chartMakeByQuarter = await responsechartMakeByQuarter.json();

    new Chart(document.getElementById("chartMakeByQuarter"), {
        type: 'bar',
        data: chartMakeByQuarter,
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