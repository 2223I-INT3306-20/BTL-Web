$(document).ready(function(){
    getSellByYear();
})
async function getSellByYear() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
        }
    };
    const responseSell = await fetch(
        'http://localhost:8080/admin/sellByYear', settings);
    const dataSell = await responseSell.json();

    new Chart(document.getElementById("daban"), {
        type: 'bar',
        data: dataSell,
        options: {
            legend: { display: false },
            title: {
                display: true,
                text: 'Make by Year'
            },
            maintainAspectRatio: true,
        }
    });

}