const patientForm = document.getElementById("patientRegisterForm");

if (patientForm) {
    patientForm.addEventListener("submit", registerPatient);
}

async function registerPatient(e) {

    e.preventDefault();

    const fullName = document.getElementById("fullName").value.trim();
    const email = document.getElementById("email").value.trim();
    const phone = document.getElementById("phone").value.trim();
    const gender = document.getElementById("gender").value;
    const dob = document.getElementById("dob").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (!fullName || !email || !phone || !gender || !dob || !password || !confirmPassword) {
        alert("Please fill all fields.");
        return;
    }

    if (phone.length !== 10 || isNaN(phone)) {
        alert("Enter a valid 10 digit phone number.");
        return;
    }

    if (password.length < 6) {
        alert("Password must be at least 6 characters.");
        return;
    }

    if (password !== confirmPassword) {
        alert("Passwords do not match.");
        return;
    }

    const patient = {
        fullName,
        email,
        phone,
        gender,
        dob,
        password
    };

    try {

        const response = await patientRegister(patient);

        if (response.success) {

            alert(response.message);

            document.getElementById("patientRegisterForm").reset();

            window.location.href = "login.html";

        } else {

            alert(response.message || "Registration Failed.");

        }

    } catch (error) {

        console.error(error);

        alert("Server Error.");

    }

}