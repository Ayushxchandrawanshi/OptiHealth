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

function getUserData() {

    const user = getUser();

    return user ? user.data : null;

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

        logout();

    }

}

function authHeader() {

    const token = getToken();

    if (!token) {

        return {};

    }

    return {

        Authorization: "Bearer " + token

    };

}

function redirectDashboard(role) {

    if (!role) {

        alert("Role not found.");

        window.location.href = "login.html";

        return;

    }

    switch (role.toUpperCase()) {

        case "ADMIN":

            window.location.href = "admin/dashboard.html";
            break;

        case "DOCTOR":

            window.location.href = "doctor/dashboard.html";
            break;

        case "PATIENT":

            window.location.href = "patient/dashboard.html";
            break;

        default:

            alert("Invalid Role");

            window.location.href = "login.html";

    }

}

function getPatientId() {

    const data = getUserData();

    return data ? data.patientId || data.id : null;

}

function getDoctorId() {

    const data = getUserData();

    return data ? data.doctorId || data.id : null;

}

function getAdminId() {

    const data = getUserData();

    return data ? data.adminId || data.id : null;

}