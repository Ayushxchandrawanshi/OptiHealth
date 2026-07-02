document.addEventListener("DOMContentLoaded", () => {

    requireRole("ADMIN");

    loadDashboard();

});

async function loadDashboard() {

    try {

        const doctors = await getAllDoctors(getToken());
        const patients = await getAllPatients(getToken());
        const appointments = await getAllAppointments(getToken());

        const totalDoctors = Array.isArray(doctors) ? doctors.length : 0;
        const totalPatients = Array.isArray(patients) ? patients.length : 0;
        const totalAppointments = Array.isArray(appointments) ? appointments.length : 0;

        let pending = 0;
        let accepted = 0;
        let rejected = 0;

        if (Array.isArray(appointments)) {

            appointments.forEach(app => {

                switch ((app.status || "").toUpperCase()) {

                    case "ACCEPTED":
                        accepted++;
                        break;

                    case "REJECTED":
                        rejected++;
                        break;

                    default:
                        pending++;

                }

            });

        }

        const doctorCard = document.getElementById("totalDoctors");
        const patientCard = document.getElementById("totalPatients");
        const appointmentCard = document.getElementById("totalAppointments");
        const pendingCard = document.getElementById("pendingAppointments");

        if (doctorCard) doctorCard.innerText = totalDoctors;
        if (patientCard) patientCard.innerText = totalPatients;
        if (appointmentCard) appointmentCard.innerText = totalAppointments;
        if (pendingCard) pendingCard.innerText = pending;

        const table = document.getElementById("recentAppointments");

        if (table) {

            table.innerHTML = "";

            if (totalAppointments === 0) {

                table.innerHTML = `
<tr>
<td colspan="5" style="text-align:center;padding:30px;">
No Appointments Found
</td>
</tr>
`;

            } else {

                appointments.slice(0, 10).forEach(app => {

                    let badge = "";

                    switch ((app.status || "").toUpperCase()) {

                        case "ACCEPTED":
                            badge = '<span class="badge success">Accepted</span>';
                            break;

                        case "REJECTED":
                            badge = '<span class="badge danger">Rejected</span>';
                            break;

                        default:
                            badge = '<span class="badge warning">Pending</span>';

                    }

                    table.innerHTML += `

<tr>

<td>${app.appointmentId}</td>

<td>${app.patientId}</td>

<td>${app.doctorId}</td>

<td>${app.appointmentDate || "-"}</td>

<td>${badge}</td>

</tr>

`;

                });

            }

        }

    } catch (error) {

        console.error(error);

        alert("Unable to load dashboard.");

    }

}