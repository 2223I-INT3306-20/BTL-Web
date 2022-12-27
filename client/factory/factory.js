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

window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple1 = document.getElementById('datatablesSimple1');
    if (datatablesSimple1) {
        new simpleDatatables.DataTable(datatablesSimple1);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple2 = document.getElementById('datatablesSimple2');
    if (datatablesSimple2) {
        new simpleDatatables.DataTable(datatablesSimple2);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple3 = document.getElementById('datatablesSimple3');
    if (datatablesSimple3) {
        new simpleDatatables.DataTable(datatablesSimple3);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple4 = document.getElementById('datatablesSimple4');
    if (datatablesSimple4) {
        new simpleDatatables.DataTable(datatablesSimple4);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple5 = document.getElementById('datatablesSimple5');
    if (datatablesSimple5) {
        new simpleDatatables.DataTable(datatablesSimple5);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple6 = document.getElementById('datatablesSimple6');
    if (datatablesSimple6) {
        new simpleDatatables.DataTable(datatablesSimple6);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple7 = document.getElementById('datatablesSimple7');
    if (datatablesSimple7) {
        new simpleDatatables.DataTable(datatablesSimple7);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple8 = document.getElementById('datatablesSimple8');
    if (datatablesSimple8) {
        new simpleDatatables.DataTable(datatablesSimple8);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple9 = document.getElementById('datatablesSimple9');
    if (datatablesSimple9) {
        new simpleDatatables.DataTable(datatablesSimple9);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple10 = document.getElementById('datatablesSimple10');
    if (datatablesSimple10) {
        new simpleDatatables.DataTable(datatablesSimple10);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple11 = document.getElementById('datatablesSimple11');
    if (datatablesSimple11) {
        new simpleDatatables.DataTable(datatablesSimple11);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    const tbProduct = document.getElementById('tbProduct');
    if (tbProduct) {
        loadDoc();
        new simpleDatatables.DataTable(tbProduct);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    const tbProduct = document.getElementById('tbWareHouse');
    if (tbWareHouse) {
        // loadDoc();
        new simpleDatatables.DataTable(tbWareHouse);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    const tbProduct = document.getElementById('tbSell');
    if (tbSell) {
        // loadDoc();
        new simpleDatatables.DataTable(tbSell);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    const tbProduct = document.getElementById('tbInsurance');
    if (tbInsurance) {
        // loadDoc();
        new simpleDatatables.DataTable(tbInsurance);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    const tbReturns = document.getElementById('tbReturns');
    if (tbReturns) {
        // loadDoc();
        new simpleDatatables.DataTable(tbReturns);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    const tbSummon = document.getElementById('tbSummon');
    if (tbSummon) {
        // loadDoc();
        new simpleDatatables.DataTable(tbSummon);
    }
});


const ctx = document.getElementById('myChart');
  new Chart(ctx, {
    type: 'bar',
            data: {
            labels: ['01','02','03','04','05','06','07', '08', '09', '10', '11', '12'],
            datasets: [{
                label: 'Macbook',
                data: [20, 18, 14, 16, 19, 28, 12, 19, 3, 5, 2, 3],
                borderWidth: 1
            }, {
                label: 'ASUS',
                data: [30, 24, 16, 12, 17, 25, 15, 16, 6, 3, 1, 4],
                borderWidth: 1
            }, {
                label: 'DELL',
                data: [24, 26, 14, 12, 18, 28, 8, 9, 6, 4, 3, 2],
                borderWidth: 1
            }
            
            ]
            },
            
            options: {
            scales: {
                y: {
                beginAtZero: true
                }
            },
            responsive: true,
            maintainAspectRatio: false
            }
  });


const ctx1 = document.getElementById('spdbtt');
new Chart(ctx1, {
    type: 'line',
    data: {
        labels: ['01','02','03','04','05','06','07','08','09','10','11','12'],
        datasets: [{
            label: 'Macbook',
            data: [12,22,33,44,55,66,80, 70, 89, 49, 60, 90],
            fill: false,
            borderColor: 'red',
            backgroundColor: 'red',
            tension: 0.1
        }, {
            label: 'ASUS',
            data: [4,12,25,60,36,56,59, 80, 81, 56, 55, 80],
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
    },
    options: {
        responsive: true,
        maintainAspectRatio: false
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
    },
    options: {
        responsive: true,
        maintainAspectRatio: false
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
    },
    options: {
        responsive: true,
        maintainAspectRatio: false
    }
});

const ctx4 = document.getElementById('myChart1');
  new Chart(ctx4, {
    type: 'bar',
            data: {
            labels: ['Quý 1', 'Quý 2', 'Quý 3', 'Quý 4'],
            datasets: [{
                label: 'Macbook',
                data: [20, 18, 14, 16],
                borderWidth: 1
            }, {
                label: 'ASUS',
                data: [30, 24, 16, 12],
                borderWidth: 1
            }, {
                label: 'DELL',
                data: [24, 26, 14, 12],
                borderWidth: 1
            }
            
            ]
            },
            
            options: {
                scales: {
                    y: {
                    beginAtZero: true
                    }
                },
                responsive: true,
                maintainAspectRatio: false
            }
  });

  const ctx5 = document.getElementById('myChart2');
  new Chart(ctx5, {
    type: 'bar',
            data: {
                labels: ['2017', '2018', '2019', '2020', '2021', '2022'],
                datasets: [{
                    label: 'Macbook',
                    data: [20, 18, 14, 16, 19, 28],
                    borderWidth: 1
                }, {
                    label: 'ASUS',
                    data: [30, 24, 16, 12, 17, 25],
                    borderWidth: 1
                }, {
                    label: 'DELL',
                    data: [24, 26, 14, 12, 18, 28],
                    borderWidth: 1
                }
            
            ]
            },
            
            options: {
            scales: {
                y: {
                beginAtZero: true
                }
            },
            responsive: true,
            maintainAspectRatio: false
            }
  });

  const ctx6 = document.getElementById('dsp');
        new Chart(ctx6, {
            type: 'pie',
            data: {
                labels: ['Macbook','ASUS','DELL'],
                datasets: [{
                    label: 'Số máy',
                    data: [300, 50, 100],
                    backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 205, 86)'
                    ],
                    hoverOffset: 4
                }]
                
            },
            options: {
                responsive: true,
                maintainAspectRatio: false
            }
        });

        const ctx7 = document.getElementById('cssx');
        new Chart(ctx7, {
            type: 'pie',
            data: {
                labels: ['Macbook','ASUS','DELL'],
                datasets: [{
                    label: 'Số máy',
                    data: [300, 50, 100],
                    backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 205, 86)'
                    ],
                    hoverOffset: 4
                }]
                
            },
            options: {
                responsive: true,
                maintainAspectRatio: false
            }
        });

        const ctx8 = document.getElementById('ttbh');
        new Chart(ctx8, {
            type: 'pie',
            data: {
                labels: ['Macbook','ASUS','DELL'],
                datasets: [{
                    label: 'Số máy',
                    data: [300, 50, 100],
                    backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 205, 86)'
                    ],
                    hoverOffset: 4
                }]
                
            },
            options: {
                responsive: true,
                maintainAspectRatio: false
            }
        });

function logout() {
    sessionStorage.removeItem('token');
    sessionStorage.clear();
}