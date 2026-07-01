const doctorForm = document.getElementById("doctorRegisterForm");

if (doctorForm) {
    doctorForm.addEventListener("submit", registerDoctor);
}

async function registerDoctor(e) {

    e.preventDefault();

    const doctorName = document.getElementById("doctorName").value.trim();
    const email = document.getElementById("email").value.trim();
    const specialization = document.getElementById("specialization").value.trim();
    const experience = document.getElementById("experience").value.trim();
    const fee = document.getElementById("fee").value.trim();
    const description = document.getElementById("description").value.trim();
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (!doctorName || !email || !specialization || !experience || !fee || !description || !password || !confirmPassword) {
        alert("Please fill all fields.");
        return;
    }

    if (isNaN(experience) || experience < 0) {
        alert("Enter valid experience.");
        return;
    }

    if (isNaN(fee) || fee <= 0) {
        alert("Enter valid consultation fee.");
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

    const doctor = {
        doctorName: doctorName,
        email: email,
        password: password,
        specialization: specialization,
        experience: parseInt(experience),
        fee: parseFloat(fee),
        description: description
    };

    try {

        const response = await doctorRegister(doctor);

        if (response.success) {

            alert(response.message);

            document.getElementById("doctorRegisterForm").reset();

            window.location.href = "login.html";

        } else {

            alert(response.message || "Registration Failed.");

        }

    } catch (error) {

        console.error(error);

        alert("Server Error. Please try again.");

    }

}