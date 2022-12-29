$(document).ready(function(){
    getDatachartXuatByQuarter();
})
async function getDatachartXuatByQuarter() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const responsechartXuatByQuarter = await fetch(
        'http://localhost:8080/factory/xuatByQuarter', settings);
    const chartXuatByQuarter = await responsechartXuatByQuarter.json();

    new Chart(document.getElementById("chartXuatByQuarter"), {
        type: 'bar',
        data: chartXuatByQuarter,
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