window.addEventListener('DOMContentLoaded', event => {
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }

});

window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple = document.getElementById('datatablesSimple');
    if (datatablesSimple) {
        new simpleDatatables.DataTable(datatablesSimple);
    }
});

const ctx1 = document.getElementById('spdbtt');
new Chart(ctx1, {
    type: 'line',
    data: {
        labels: ['01/2022','02/2022','03/2022','04/2022','05/2022','0/2022','07/2022','08/2022','09/2022','10/2022','11/2022','12/2022'],
        datasets: [{
            label: 'Macbook',
            data: [12,22,33,44,55,66,80, 70, 89, 49, 60, 90],
            fill: false,
            borderColor: 'red',
            backgroundColor: 'red',
            tension: 0.1
        }, {
            label: 'ASUS',
            data: [04,12,25,60,36,56,59, 80, 81, 56, 55, 80],
            fill: false,
            borderColor: 'blue',
            backgroundColor: 'blue',
            tension: 0.1
        },  {
            label: 'DELL',
            data: [11,25,36,42,15,28,70, 84, 86, 60, 66, 87],
            fill: false,
            borderColor: 'green',
            backgroundColor: 'green',
            tension: 0.1
        }
        ]
    }
});

const ctx2 = document.getElementById('spdbtq');
new Chart(ctx2, {
    type: 'line',
    data: {
        labels: ['Quý 1/2022','Quý 2/2022','Quý 3/2022','Quý 4/2022'],
        datasets: [{
            label: 'Macbook',
            data: [67,100,125,140],
            fill: false,
            borderColor: 'red',
            backgroundColor: 'red',
            tension: 0.1
        }, {
            label: 'ASUS',
            data: [41,120, 80,130],
            fill: false,
            borderColor: 'blue',
            backgroundColor: 'blue',
            tension: 0.1
        },  {
            label: 'DELL',
            data: [80,160, 150,200],
            fill: false,
            borderColor: 'green',
            backgroundColor: 'green',
            tension: 0.1
        }
        ]
    }
});

const ctx3 = document.getElementById('spdbtn');
new Chart(ctx3, {
    type: 'line',
    data: {
        labels: ['2017','2018','2019','2020','2021','2022'],
        datasets: [{
            label: 'Macbook',
            data: [80, 70, 89, 49, 60, 90],
            fill: false,
            borderColor: 'red',
            backgroundColor: 'red',
            tension: 0.1
        }, {
            label: 'ASUS',
            data: [59, 80, 81, 56, 55, 80],
            fill: false,
            borderColor: 'blue',
            backgroundColor: 'blue',
            tension: 0.1
        },  {
            label: 'DELL',
            data: [70, 84, 86, 60, 66, 87],
            fill: false,
            borderColor: 'green',
            backgroundColor: 'green',
            tension: 0.1
        }
        ]
    }
});