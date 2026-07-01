const loginForm = document.getElementById("loginForm");

if (loginForm) {
    loginForm.addEventListener("submit", login);
}

async function login(e) {

    e.preventDefault();

    const role = document.getElementById("role").value;
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    if (role === "") {
        alert("Please select your role.");
        return;
    }

    if (email === "") {
        alert("Please enter your email.");
        return;
    }

    if (password === "") {
        alert("Please enter your password.");
        return;
    }

    const loginData = {
        email: email,
        password: password
    };

    let response = null;

    try {

        switch (role) {

            case "PATIENT":
                response = await patientLogin(loginData);
                break;

            case "DOCTOR":
                response = await doctorLogin(loginData);
                break;

            case "ADMIN":
                response = await adminLogin(loginData);
                break;

            default:
                alert("Invalid Role");
                return;

        }

        if (response && response.success) {

            saveLogin(response);

            alert(response.message);

            redirectDashboard(response.role);

        }
        else {

            alert(response.message || "Login Failed");

        }

    } catch (error) {

        console.error(error);

        alert("Server Error. Please try again.");

    }
}