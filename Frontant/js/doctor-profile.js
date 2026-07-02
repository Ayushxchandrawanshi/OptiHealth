document.addEventListener("DOMContentLoaded", () => {

    requireRole("DOCTOR");

    loadDoctorProfile();

});

function loadDoctorProfile() {

    const doctor = getUserData();

    if (!doctor) {
        window.location.href = "../login.html";
        return;
    }

    document.getElementById("doctorName").innerText =
        doctor.doctorName || "Doctor";

    document.getElementById("doctorNameField").value =
        doctor.doctorName || "";

    document.getElementById("email").value =
        doctor.email || "";

    document.getElementById("specialization").value =
        doctor.specialization || "";

    document.getElementById("experience").value =
        doctor.experience || "";

    document.getElementById("fee").value =
        doctor.fee || "";

    document.getElementById("password").value =
        doctor.password || "";

    document.getElementById("description").value =
        doctor.description || "";

}