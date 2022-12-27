$(document).ready(function(){
    getData();
})
async function getData() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const response = await fetch(
        'http://localhost:8080/factory/makeByMonth', settings);
    const data = await response.json();

    size = data.length;

    labels = [];
    values = [];
    for (i = 0; i < size; i++) {
        labels.push(data[i].label);
        values.push(data[i].quantity);
    }


    new Chart(document.getElementById("myChart"), {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    label: "Mades",
                    backgroundColor: ["#3e95cd",
                        "#8e5ea2",
                        "#3cba9f",
                        "#e8c3b9",
                        "#c45850",
                        "#CD5C5C",
                        "#40E0D0"],
                    data: values
                }
            ]
        },
        options: {
            legend: { display: false },
            title: {
                display: true,
                text: 'Product by Month'
            },
            maintainAspectRatio: false
        }
    });

}