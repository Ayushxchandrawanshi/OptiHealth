document.addEventListener("DOMContentLoaded", loadDashboard);

async function loadDashboard() {
    const user = getUser();

    if (!user) {
        window.location.href = "../login.html";
        return;
    }

    try {
        const patientId = getPatientId();
        const token = getToken();
        console.log("Patient ID :", patientId);
        const appointments = await getPatientAppointments(patientId, token);
        console.log(appointments);

        if (!appointments || !Array.isArray(appointments)) {
            return;
        }

        let total = appointments.length;
        let pending = 0;
        let accepted = 0;
        let completed = 0;
        const today = new Date();
        appointments.forEach(appointment => {

            if (appointment.status === "PENDING") {
                pending++;
            }

            if (appointment.status === "ACCEPTED") {
                accepted++;

                if (appointment.appointmentDate) {
                    const appointmentDate = new Date(appointment.appointmentDate);

                    if (appointmentDate < today) {
                        completed++;
                    }
                }
            }
        });
        document.getElementById("totalAppointments").innerText = total;
        document.getElementById("upcomingAppointments").innerText = accepted;
        document.getElementById("completedAppointments").innerText = completed;
        document.getElementById("pendingAppointments").innerText = pending;
        const table = document.getElementById("appointmentTable");

        if (table) {
            table.innerHTML = "";

            appointments.forEach(appointment => {
                let badge = "warning";
                let status = appointment.status;

                if (appointment.status === "ACCEPTED") {
                    badge = "success";
                }

                if (appointment.status === "REJECTED") {
                    badge = "danger";
                }

                table.innerHTML += `
<tr>
<td>#APT${appointment.appointmentId}</td>
<td>Doctor ID : ${appointment.doctorId}</td>
<td>${appointment.appointmentDate || "-"}</td>
<td>${appointment.appointmentTime || "-"}</td>
<td><span class="badge ${badge}">${status}</span></td>
</tr>
`;
            });
        }

    } catch (error) {
        console.error("Dashboard Error :", error);
    }
}

/* ===========================
   Show All Doctors
=========================== */
async function showAllDoctors() {

    console.log("View All Clicked");

    const section = document.getElementById("allDoctorsSection");
    const container = document.getElementById("dashboardDoctorsContainer");
    const button = document.getElementById("viewAllBtn");

    if (section.style.display === "block") {
        section.style.display = "none";
        button.innerHTML = "View All";
        return;
    }

    section.style.display = "block";
    button.innerHTML = "Hide";

    try {
        const doctors = await getDoctors(getToken());
        container.innerHTML = "";
        if (!doctors || doctors.length === 0) {
            container.innerHTML = `
                <h3>No Doctors Available</h3>
            `;
            return;
        }

        doctors.forEach((doctor, index) => {
            let image = "../assets/images/default-doctor.jpg";

            if (index === 0) image = "../assets/images/doctor1.jpg";
            if (index === 1) image = "../assets/images/doctor2.jpg";
            if (index === 2) image = "../assets/images/doctor3.jpg";
            if (index === 3) image = "../assets/images/doctor4.jpg";
            if (index === 4) image = "../assets/images/doctor5.jpg";
            if (index === 5) image = "../assets/images/doctor6.jpg";
            container.innerHTML += `

<div class="doctor-card">
    <img src="${image}" class="doctor-img">
    <h3>${doctor.doctorName}</h3>
    <p><strong>${doctor.specialization}</strong></p>
    <p>${doctor.experience} Years Experience</p>
    <p>₹${doctor.fee}</p>
    <p>${doctor.description}</p>

    <div class="doctor-buttons">
        <button class="btn btn-outline">
            View Details
        </button>

        <button class="btn btn-primary"
                onclick="location.href='book-appointment.html?doctorId=${doctor.doctorId}'">
            Book Appointment
        </button>
    </div>
</div>
`;

        });
    } catch (error) {
        console.error(error);
    }
}