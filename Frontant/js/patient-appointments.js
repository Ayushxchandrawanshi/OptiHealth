document.addEventListener("DOMContentLoaded", () => {

    requireRole("PATIENT");

    loadAppointments();

});

async function loadAppointments() {

    const user = getUserData();

    if (!user) {
        return;
    }

    try {

        const appointments = await getPatientAppointments(user.id, getToken());

        const table = document.getElementById("appointmentTable");

        table.innerHTML = "";

        let total = 0;
        let pending = 0;
        let confirmed = 0;
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
            document.getElementById("confirmedAppointments").innerText = 0;
            document.getElementById("pendingAppointments").innerText = 0;
            document.getElementById("rejectedAppointments").innerText = 0;

            return;

        }

        appointments.forEach(app => {

            total++;

            let badge = "";

            switch ((app.status || "").toUpperCase()) {

                case "ACCEPTED":
                    confirmed++;
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

<td>${app.doctorId}</td>

<td>${app.bookingDate || "-"}</td>

<td>${app.appointmentDate || "-"}</td>

<td>${app.appointmentTime || "-"}</td>

<td>${app.reason || "-"}</td>

<td>${badge}</td>

<td>${app.remarks || "-"}</td>

</tr>

`;

        });

        document.getElementById("totalAppointments").innerText = total;
        document.getElementById("confirmedAppointments").innerText = confirmed;
        document.getElementById("pendingAppointments").innerText = pending;
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