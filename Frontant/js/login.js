const loginForm = document.getElementById("loginForm");
if (loginForm) {
    loginForm.addEventListener("submit", login);
}

async function login(e) {
    e.preventDefault();

    const role = document.getElementById("role").value;
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    if (email === "" || password === "") {
        alert("Please enter email and password.");
        return;
    }

    let response;
    const data = {
        email: email,
        password: password
    };

    switch (role) {
        case "PATIENT":
            response = await patientLogin(data);
            break;
        case "DOCTOR":
            response = await doctorLogin(data);
            break;
        case "ADMIN":
            response = await adminLogin(data);
            break;
        default:
            alert("Please select role.");
            return;
    }

    if (response.success) {
        saveLogin(response);
        alert(response.message);
        redirectDashboard(response.role);
    } else {
        alert(response.message);
    }

}