function logout() {
    sessionStorage.removeItem('token');
    sessionStorage.clear();
}