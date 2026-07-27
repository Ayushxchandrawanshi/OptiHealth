document.addEventListener("DOMContentLoaded", () => {
    requireRole("PATIENT");
    loadProfile();
});

function loadProfile() {

    const token = localStorage.getItem("token");
    const patientId = getPatientId();

    if (!token || !patientId) {
        window.location.href = "../login.html";
        return;
    }
    getPatientProfile(patientId, token)
        .then(patient => {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
        .then(response => response.json())
        .then(patient => {

            document.getElementById("profileName").textContent = patient.fullName || "";
            document.getElementById("fullName").value = patient.fullName || "";
            document.getElementById("email").value = patient.email || "";
            document.getElementById("phone").value = patient.phone || "";
            document.getElementById("gender").value = patient.gender || "";
            document.getElementById("dob").value = patient.dob || "";

        })
        .catch(error => {
            console.error(error);
            alert("Unable to load profile.");
        });

}

function enableEdit() {
    document.getElementById("fullName").removeAttribute("readonly");
    document.getElementById("phone").removeAttribute("readonly");
    document.getElementById("gender").removeAttribute("readonly");
    document.getElementById("dob").removeAttribute("readonly");
    document.getElementById("saveProfileBtn").style.display = "inline-block";
}

function updateProfile() {
    const token = localStorage.getItem("token");
    const patientId = getPatientId();

    const patient = {
        fullName: document.getElementById("fullName").value,
        email: document.getElementById("email").value,
        phone: Number(document.getElementById("phone").value),
        gender: document.getElementById("gender").value,
        dob: document.getElementById("dob").value
    };

    updatePatientProfile(patientId, patient, token)
        .then(response => {

            method: "PUT",

                headers: {
                "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`
            },
            body: JSON.stringify(patient)
        })

        .then(response => {
            if (response.ok) {
                alert("Profile Updated Successfully");
                location.reload();
            } else {
                alert("Profile Update Failed");
            }
        })
        .catch(error => {
            console.error(error);
            alert("Something went wrong.");
        });
}