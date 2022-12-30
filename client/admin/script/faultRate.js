$(document).ready(function(){
    getFault();
})
async function getFault() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const responseFault = await fetch(
        'http://localhost:8080/admin/faultByYear', settings);
    const fault = await responseFault.json();

    new Chart(document.getElementById('dabh'), {
        type: 'pie',
        data: {
            labels: fault.labels,
            datasets: [{
                label: 'Số máy',
                data: fault.datasets,
                backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 205, 86)'
                ],
                hoverOffset: 4
            }]

        },
    });

}