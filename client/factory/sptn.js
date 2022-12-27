$(document).ready(function(){
    getData2();
})
async function getData2() {
    const settings = {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.sessionStorage.getItem('token'),
            'Username': window.sessionStorage.getItem('username')
        }
    };
    const response1 = await fetch(
        'http://localhost:8080/factory/makeByYear', settings);
    const data1 = await response1.json();

    size1 = data1.length;

    labels1 = [];
    values1 = [];
    for (i = 0; i < size1; i++) {
        labels1.push(data1[i].label);
        values1.push(data1[i].quantity);
    }


    new Chart(document.getElementById("myChart2"), {
        type: 'bar',
        data: {
            labels: labels1,
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
                    data: values1
                }
            ]
        },
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