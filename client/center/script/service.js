window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple = document.getElementById('tbInsurance');
    if (datatablesSimple) {
        new simpleDatatables.DataTable(datatablesSimple);
    }
});

function logout() {
    sessionStorage.removeItem('token');
    sessionStorage.clear();
}