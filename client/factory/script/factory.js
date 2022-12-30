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
    const datatablesSimple = document.getElementById('listXuatMonth');
    if (datatablesSimple) {
        new simpleDatatables.DataTable(datatablesSimple);
    }
});


window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple4 = document.getElementById('listXuatMonth');
    if (datatablesSimple4) {
        new simpleDatatables.DataTable(datatablesSimple4);
    }
});


window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple5 = document.getElementById('listXuatQuarter');
    if (datatablesSimple5) {
        new simpleDatatables.DataTable(datatablesSimple5);
    }
});


window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple5 = document.getElementById('listXuatYear');
    if (datatablesSimple5) {
        new simpleDatatables.DataTable(datatablesSimple5);
    }
});


window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple5 = document.getElementById('chartXuatByQuarter');
    if (datatablesSimple5) {
        new simpleDatatables.DataTable(datatablesSimple5);
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
    const datatablesSimple = document.getElementById('listMakeByYear');
    if (datatablesSimple) {
        new simpleDatatables.DataTable(datatablesSimple);
    }
});

window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple = document.getElementById('listMakeByQuarter');
    if (datatablesSimple) {
        new simpleDatatables.DataTable(datatablesSimple);
    }
});


window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple = document.getElementById('listMakeByMonth');
    if (datatablesSimple) {
        new simpleDatatables.DataTable(datatablesSimple);
    }
});

function xuatMonth() {
    getDataMonth();
    getDataXuatTT();
}

function xuatQuarter() {
    getDataQuarter();
    getDatachartXuatByQuarter();
}

function xuatYear() {
    getDataXuatTN();
    getDataXuatTN();
}

function makeMonth() {
    getDataMakeByMonth();
    getchartMakeTT();
}

function makeQuarter() {
    getDatachartMakeByQuarter();
    getDataMakeByQuarter();
}

function makeYear() {
    getDataMakeByYear();
    getData2();
}



function logout() {
    sessionStorage.removeItem('token');
    sessionStorage.clear();
}

