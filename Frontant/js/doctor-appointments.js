document.addEventListener("DOMContentLoaded", () => {

    requireRole("DOCTOR");

    loadAppointments();

});

async function loadAppointments() {

    const doctor = getUserData();

    if (!doctor) {
        return;
    }

    try {

        const appointments = await getDoctorAppointments(doctor.id, getToken());

        const table = document.getElementById("appointmentTable");

        table.innerHTML = "";

        let total = 0;
        let pending = 0;
        let accepted = 0;
        let rejected = 0;

        if (!appointments || appointments.length === 0) {

            table.innerHTML = `
<tr>
<td colspan="8" style="text-align:center;padding:30px;">
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

        appointments.forEach(app => {

            total++;

            let badge = "";

            switch ((app.status || "").toUpperCase()) {

                case "ACCEPTED":
                    accepted++;
                    badge = `<span class="badge success">Accepted</span>`;
                    break;

                case "REJECTED":
                    rejected++;
                    badge = `<span class="badge danger">Rejected</span>`;
                    break;

                default:
                    pending++;
                    badge = `<span class="badge warning">Pending</span>`;

            }

            table.innerHTML += `

<tr>

<td>${app.appointmentId}</td>

<td>${app.patientId}</td>

<td>${app.bookingDate || "-"}</td>

<td>${app.appointmentDate || "-"}</td>

<td>${app.appointmentTime || "-"}</td>

<td>${app.reason || "-"}</td>

<td>${badge}</td>

<td>

<button class="doctor-btn"
onclick="acceptAppointmentAction(${app.appointmentId})">

Accept

</button>

<button class="delete-btn"
style="margin-left:8px;"
onclick="rejectAppointmentAction(${app.appointmentId})">

Reject

</button>

</td>

</tr>

`;

        });

        document.getElementById("totalAppointments").innerText = total;
        document.getElementById("pendingAppointments").innerText = pending;
        document.getElementById("acceptedAppointments").innerText = accepted;
        document.getElementById("rejectedAppointments").innerText = rejected;

    } catch (error) {

        console.error(error);

        document.getElementById("appointmentTable").innerHTML = `
<tr>
<td colspan="8" style="text-align:center;color:red;padding:30px;">
Failed to Load Appointments
</td>
</tr>
`;

    }

}

async function acceptAppointmentAction(id) {

    const date = prompt("Enter Appointment Date (YYYY-MM-DD)");

    if (!date) {
        return;
    }

    const time = prompt("Enter Appointment Time (HH:MM)");

    if (!time) {
        return;
    }

    try {

        const response = await acceptAppointment(
            id,
            date,
            time,
            getToken()
        );

        alert(response.message);

        loadAppointments();

    } catch (error) {

        console.error(error);

        alert("Unable to Accept Appointment.");

    }

}

async function rejectAppointmentAction(id) {

    if (!confirm("Reject this appointment?")) {
        return;
    }

    try {

        const response = await rejectAppointment(
            id,
            getToken()
        );

        alert(response.message);

        loadAppointments();

    } catch (error) {

        console.error(error);

        alert("Unable to Reject Appointment.");

    }

}