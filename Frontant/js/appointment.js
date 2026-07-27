async function loadDoctors() {
    const token = localStorage.getItem("token");
    const response = await getDoctors(token);
    const doctorSelect = document.getElementById("doctorId");
    doctorSelect.innerHTML = "<option value=''>Choose Doctor</option>";
    if (!response || !Array.isArray(response)) return;

    response.forEach(doctor => {
        const option = document.createElement("option");
        option.value = doctor.id;
        option.text = `Dr. ${doctor.doctorName} - ${doctor.specialization}`;
        option.dataset.department = doctor.specialization;
        doctorSelect.appendChild(option);
    });

    const params = new URLSearchParams(window.location.search);
    const selectedDoctor = params.get("doctorId");

    if (selectedDoctor) {
        doctorSelect.value = selectedDoctor;
        const option = doctorSelect.options[doctorSelect.selectedIndex];
        document.getElementById("department").value = option.dataset.department || "";
    }

    doctorSelect.addEventListener("change", () => {
        const selected = doctorSelect.options[doctorSelect.selectedIndex];
        document.getElementById("department").value = selected.dataset.department || "";
    });
}

async function bookAppointment() {
    const token = localStorage.getItem("token");
    const patientId = getPatientId();
    const doctorId = document.getElementById("doctorId").value;
    const appointmentDate = document.getElementById("appointmentDate").value;
    const appointmentTime = document.getElementById("appointmentTime").value;
    const reason = document.getElementById("reason").value.trim();
    const remarks = document.getElementById("remarks").value.trim();

    if (!doctorId || !appointmentDate || !appointmentTime || !reason) {
        alert("Please fill all required fields.");
        return;
    }

    const appointment = {
        doctorId: Number(doctorId),
        patientId: Number(patientId),
        bookingDate: new Date().toISOString().split("T")[0],
        appointmentDate,
        appointmentTime: appointmentTime + ":00",
        status: "PENDING",
        reason,
        remarks
    };

    const response = await createPatientAppointment(appointment, token);
    if (response.success) {
        alert("Appointment booked successfully.");
        document.getElementById("appointmentForm").reset();
    }
    else {
        alert(response.message || "Booking Failed");
    }
}

document.addEventListener("DOMContentLoaded", () => {
    loadDoctors();
});