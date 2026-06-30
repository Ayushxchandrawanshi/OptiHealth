const TOKEN_KEY = "token";
const USER_KEY = "user";

function saveLogin(loginData) {
    localStorage.setItem(TOKEN_KEY, loginData.token);
    localStorage.setItem(USER_KEY, JSON.stringify(loginData));
}

function getToken() {
    return localStorage.getItem(TOKEN_KEY);
}

function getUser() {
    const user = localStorage.getItem(USER_KEY);
    return user ? JSON.parse(user) : null;
}

function getRole() {
    const user = getUser();
    if (!user) return null;
    return (user.role || "").toUpperCase();
}

function isLoggedIn() {
    return getToken() != null;
}

function logout() {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    window.location.href = "../login.html";
}

function requireLogin() {
    if (!isLoggedIn()) {
        alert("Please login first.");
        window.location.href = "../login.html";
    }
}

function requireRole(role) {
    requireLogin();

    const userRole = getRole();

    if (userRole !== role.toUpperCase()) {
        alert("Unauthorized Access");
        window.location.href = "../login.html";
    }
}

function authHeader() {
    const token = getToken();
    return token ? { "Authorization": "Bearer " + token } : {};
}

function redirectDashboard(role) {

    role = role.toUpperCase();

    if (role === "ADMIN") {
        window.location.href = "admin/dashboard.html";
    }
    else if (role === "DOCTOR") {
        window.location.href = "doctor/dashboard.html";
    }
    else if (role === "PATIENT") {
        window.location.href = "patient/dashboard.html";
    }
    else {
        alert("Invalid Role");
        window.location.href = "login.html";
    }

}