document.addEventListener("DOMContentLoaded", function () {
    requireRole("ADMIN");
    loadAppointments();
});

async function loadAppointments() {
    try {
        const appointments = await getAllAppointments(getToken());
        const table = document.getElementById("appointmentTable");
        table.innerHTML = "";
        let total = 0;
        let pending = 0;
        let accepted = 0;
        let rejected = 0;
        if (!appointments || appointments.length === 0) {
            table.innerHTML = `<tr><td colspan="8" style="text-align:center;">No Appointments Found</td></tr>`;
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
                    badge = '<span class="badge success">Accepted</span>';
                    break;
                case "REJECTED":
                    rejected++;
                    badge = '<span class="badge danger">Rejected</span>';
                    break;
                default:
                    pending++;
                    badge = '<span class="badge warning">Pending</span>';
            }
            table.innerHTML += `
<tr>
<td>${app.appointmentId}</td>
<td>${app.patientId}</td>
<td>${app.doctorId}</td>
<td>${app.bookingDate || "-"}</td>
<td>${app.appointmentDate || "-"}</td>
<td>${app.appointmentTime || "-"}</td>
<td>${badge}</td>
<td>
<button class="doctor-btn" onclick="viewAppointment(${app.appointmentId},${app.patientId},${app.doctorId},'${app.bookingDate || ""}','${app.appointmentDate || ""}','${app.appointmentTime || ""}','${app.status || ""}',\`${app.reason || ""}\`,\`${app.remarks || ""}\`)">
View
</button>
<button class="delete-btn" onclick="deleteAppointmentRecord(${app.appointmentId})">
Delete
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
        alert("Unable to Load Appointments");
    }
}

function viewAppointment(id, patientId, doctorId, bookingDate, appointmentDate, appointmentTime, status, reason, remarks) {
    document.getElementById("appointmentId").value = id;
    document.getElementById("patientId").value = patientId;
    document.getElementById("doctorId").value = doctorId;
    document.getElementById("bookingDate").value = bookingDate;
    document.getElementById("appointmentDate").value = appointmentDate;
    document.getElementById("appointmentTime").value = appointmentTime;
    document.getElementById("status").value = status;
    document.getElementById("reason").value = reason;
    document.getElementById("remarks").value = remarks;
    document.getElementById("appointmentModal").style.display = "flex";
    document.getElementById("deleteAppointmentBtn").onclick = function () {
        deleteAppointmentRecord(id);
    };
}

function closeAppointmentModal() {
    document.getElementById("appointmentModal").style.display = "none";
}

async function deleteAppointmentRecord(id) {
    if (!confirm("Delete this appointment?")) {
        return;
    }
    try {
        const response = await deleteAppointment(id, getToken());
        alert(response.message || "Appointment Deleted Successfully");
        closeAppointmentModal();
        loadAppointments();
    } catch (error) {
        console.error(error);
        alert("Unable to Delete Appointment");
    }
}

function searchAppointment() {
    const input = document.getElementById("searchAppointment").value.toLowerCase();
    const rows = document.querySelectorAll("#appointmentTable tr");
    rows.forEach(row => {
        row.style.display = row.innerText.toLowerCase().includes(input) ? "" : "none";
    });
}