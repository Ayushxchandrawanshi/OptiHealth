/* ===========================
   Doctor Registration
=========================== */

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

            doctorForm.reset();

            window.location.href = "login.html";

        } else {

            alert(response.message || "Registration Failed.");

        }

    } catch (error) {

        console.error(error);

        alert("Server Error. Please try again.");

    }

}

/* ===========================
   Doctors Page
=========================== */

document.addEventListener("DOMContentLoaded", () => {

    const container = document.getElementById("doctorContainer");

    if (container) {
        loadDoctors();
    }

});

async function loadDoctors() {

    try {

        const doctors = await getDoctors();

        const container = document.getElementById("doctorContainer");

        if (!container) {
            return;
        }

        container.innerHTML = "";

        if (!doctors || doctors.length === 0) {

            container.innerHTML = `
<div class="card">
<h3>No Doctors Available</h3>
</div>
`;

            return;
        }

        doctors.forEach((doctor, index) => {

            let image = "assets/images/default-doctor.jpg";

            if (index === 0) image = "assets/images/doctor1.jpg";
            if (index === 1) image = "assets/images/doctor2.jpg";
            if (index === 2) image = "assets/images/doctor3.jpg";
            if (index === 2) image = "assets/images/doctor4.jpg";
            if (index === 2) image = "assets/images/doctor5.jpg";

            container.innerHTML += `
<div class="doctor-card">

<img src="${image}" class="doctor-img">

<h3>${doctor.doctorName}</h3>

<p><strong>${doctor.specialization}</strong></p>

<p>${doctor.experience} Years Experience</p>

<p>₹${doctor.fee}</p>

<p>${doctor.description}</p>

<a href="login.html" class="btn btn-primary">
Book Appointment
</a>

</div>
`;

        });

    } catch (error) {

        console.error(error);

    }

}

/* ===========================
   Search Doctor
=========================== */

function searchDoctors() {

    const value = document.getElementById("searchDoctor").value.toLowerCase();

    const cards = document.querySelectorAll(".doctor-card");

    cards.forEach(card => {

        const text = card.innerText.toLowerCase();

        card.style.display = text.includes(value) ? "block" : "none";

    });

}