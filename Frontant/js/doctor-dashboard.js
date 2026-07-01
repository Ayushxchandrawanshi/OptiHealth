document.addEventListener("DOMContentLoaded", () => {

    requireRole("DOCTOR");

    loadDoctorDashboard();

});

async function loadDoctorDashboard() {

    const doctor = getUserData();

    if (!doctor) {
        return;
    }

    const response = await getDoctorAppointments(doctor.id, getToken());

    if (!response) {
        return;
    }

    const table = document.getElementById("appointmentTable");

    let pending = 0;
    let accepted = 0;
    let rejected = 0;

    table.innerHTML = "";

    if (response.length === 0) {

        table.innerHTML = `
<tr>
<td colspan="6" style="text-align:center;">
No Appointments Found
</td>
</tr>
`;

        document.getElementById("totalAppointments").innerText = 0;
        document.getElementById("pendingAppointments").innerText = 0;
        document.getElementById("acceptedAppointments").innerText = 0;
        document.getElementById("rejectedAppointments").innerText = 0;

        return;

    }

    response.forEach(app => {

        if (app.status === "PENDING") pending++;

        if (app.status === "ACCEPTED") accepted++;

        if (app.status === "REJECTED") rejected++;

        table.innerHTML += `

<tr>

<td>${app.patientId}</td>

<td>${app.appointmentDate}</td>

<td>${app.appointmentTime}</td>

<td>${app.reason}</td>

<td>${app.status}</td>

<td>

<button class="btn btn-success"
onclick="acceptAppointmentAction(${app.appointmentId})">

Accept

</button>

<button class="btn btn-danger"
onclick="rejectAppointmentAction(${app.appointmentId})">

Reject

</button>

</td>

</tr>

`;

    });

    document.getElementById("totalAppointments").innerText = response.length;

    document.getElementById("pendingAppointments").innerText = pending;

    document.getElementById("acceptedAppointments").innerText = accepted;

    document.getElementById("rejectedAppointments").innerText = rejected;

}

async function acceptAppointmentAction(id) {

    const date = prompt("Enter Appointment Date (YYYY-MM-DD)");

    if (!date) return;

    const time = prompt("Enter Appointment Time (HH:MM)");

    if (!time) return;

    const response = await acceptAppointment(
        id,
        date,
        time,
        getToken()
    );

    alert(response.message);

    loadDoctorDashboard();

}

async function rejectAppointmentAction(id) {

    if (!confirm("Reject this appointment?")) {
        return;
    }

    const response = await rejectAppointment(
        id,
        getToken()
    );

    alert(response.message);

    loadDoctorDashboard();

}