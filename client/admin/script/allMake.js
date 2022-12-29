$(document).ready(function(){
    getMakeByYear();
})
async function getMakeByYear() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
        }
    };
    const responseMake = await fetch(
        'http://localhost:8080/admin/makeByYear', settings);
    const dataMake = await responseMake.json();

    new Chart(document.getElementById("dasx"), {
        type: 'bar',
        data: dataMake,
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