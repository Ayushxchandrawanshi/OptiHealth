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

    if (fullName === "" || email === "" || phone === "" || gender === "" || dob === "" || password === "") {
        alert("Please fill all fields.");
        return;
    }

    if (phone.length !== 10) {
        alert("Phone number must be 10 digits.");
        return;
    }

    if (password !== confirmPassword) {
        alert("Passwords do not match.");
        return;
    }

    const patient = {
        fullName: fullName,
        email: email,
        phone: phone,
        gender: gender,
        dob: dob,
        password: password
    };

    const response = await patientRegister(patient);

    if (response.success) {

        alert(response.message);

        window.location.href = "login.html";

    } else {

        alert(response.message);

    }

}