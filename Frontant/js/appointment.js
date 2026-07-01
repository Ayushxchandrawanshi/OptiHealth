async function bookAppointment() {

    const doctorId = document.getElementById("doctorId").value;
    const department = document.getElementById("department").value;
    const appointmentDate = document.getElementById("appointmentDate").value;
    const timeSlot = document.getElementById("timeSlot").value;
    const priority = document.getElementById("priority").value;
    const age = document.getElementById("age").value;
    const symptoms = document.getElementById("symptoms").value.trim();
    const description = document.getElementById("description").value.trim();
    const medicalHistory = document.getElementById("medicalHistory").value.trim();

    if (!doctorId || !department || !appointmentDate || !timeSlot || !age || !symptoms || !description) {
        alert("Please fill all required fields.");
        return;
    }

    const appointment = {
        doctorId,
        department,
        appointmentDate,
        timeSlot,
        priority,
        age,
        symptoms,
        description,
        medicalHistory
    };
    try {

        const response = await apiRequest(
            "/appointments/book",
            "POST",
            appointment,
            authHeader()
        );

        if (response.success) {
            alert(response.message);
            document.getElementById("appointmentForm").reset();
        } else {
            alert(response.message || "Booking failed.");
        }

    } catch (error) {
        console.error(error);
        alert("Unable to book appointment.");
    }
}

async function loadAppointments() {
    const table = document.getElementById("appointmentTable");
    if (!table) return;
    try {
        const appointments = await apiRequest(
            "/appointments/patient",
            "GET",
            null,
            authHeader()
        );
        table.innerHTML = "";
        appointments.forEach((appointment, index) => {
            table.innerHTML += `
<tr>
<td>${index + 1}</td>
<td>${appointment.doctorName}</td>
<td>${appointment.department}</td>
<td>${appointment.appointmentDate}</td>
<td>${appointment.timeSlot}</td>
<td>${appointment.status}</td>
<td>
<button class="doctor-btn" onclick="cancelAppointment(${appointment.id})">
Cancel
</button>
</td>
</tr>
`;
        });
    } catch (error) {
        console.error(error);
    }
}
async function cancelAppointment(id) {

    if (!confirm("Cancel this appointment?")) return;
    try {
        const response = await apiRequest(
            "/appointments/" + id,
            "DELETE",
            null,
            authHeader()
        );
        alert(response.message);
        loadAppointments();
    } catch (error) {
        console.error(error);
        alert("Unable to cancel appointment.");
    }
}

document.addEventListener("DOMContentLoaded", () => {
    loadAppointments();
});