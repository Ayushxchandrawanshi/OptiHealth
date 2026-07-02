document.addEventListener("DOMContentLoaded", () => {

    requireRole("PATIENT");

    loadProfile();

});

function loadProfile() {

    const patient = getUserData();

    if (!patient) {
        window.location.href = "../login.html";
        return;
    }

    document.getElementById("profileName").innerText = patient.fullName || "Patient";

    document.getElementById("fullName").value = patient.fullName || "";

    document.getElementById("email").value = patient.email || "";

    document.getElementById("phone").value = patient.phone || "";

    document.getElementById("gender").value = patient.gender || "";

    document.getElementById("dob").value = patient.dob || "";

    document.getElementById("password").value = patient.password || "";

}