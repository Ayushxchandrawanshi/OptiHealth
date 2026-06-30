const doctorForm = document.getElementById("doctorRegisterForm");

if (doctorForm) {
    doctorForm.addEventListener("submit", registerDoctor);
}

async function registerDoctor(e) {

    e.preventDefault();

    const fullName = document.getElementById("fullName").value.trim();
    const email = document.getElementById("email").value.trim();
    const phone = document.getElementById("phone").value.trim();
    const gender = document.getElementById("gender").value;
    const specialization = document.getElementById("specialization").value.trim();
    const qualification = document.getElementById("qualification").value.trim();
    const experience = document.getElementById("experience").value.trim();
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (fullName === "" || email === "" || phone === "" || gender === "" || specialization === "" || qualification === "" || experience === "" || password === "") {
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

    const doctor = {
        fullName: fullName,
        email: email,
        phone: phone,
        gender: gender,
        specialization: specialization,
        qualification: qualification,
        experience: experience,
        password: password
    };

    try {

        const response = await doctorRegister(doctor);

        if (response.success) {
            alert(response.message);
            window.location.href = "login.html";
        } else {
            alert(response.message);
        }

    } catch (error) {

        console.error(error);
        alert("Registration failed. Please try again.");

    }

}