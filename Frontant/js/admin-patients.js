document.addEventListener("DOMContentLoaded", function () {
    requireRole("ADMIN");
    loadPatients();
    const form = document.getElementById("patientForm");
    if (form) {
        form.addEventListener("submit", savePatient);
    }
});

async function loadPatients() {
    try {
        const patients = await getAllPatients(getToken());
        const table = document.getElementById("patientTable");
        table.innerHTML = "";
        if (!patients || patients.length === 0) {
            table.innerHTML = `<tr><td colspan="7" style="text-align:center;">No Patients Found</td></tr>`;
            return;
        }
        patients.forEach(p => {
            table.innerHTML += `
<tr>
<td>${p.id}</td>
<td>${p.fullName}</td>
<td>${p.email}</td>
<td>${p.phone}</td>
<td>${p.gender}</td>
<td>${p.dob}</td>
<td>
<button class="doctor-btn" onclick="fillPatient(${p.id},'${p.fullName}','${p.email}','${p.phone}','${p.password}','${p.gender}','${p.dob}')">
Edit
</button>
<button class="delete-btn" onclick="deletePatientRecord(${p.id})">
Delete
</button>
</td>
</tr>
`;
        });
    } catch (error) {
        console.error(error);
        alert("Unable to Load Patients");
    }
}

function openPatientModal() {
    document.getElementById("modalTitle").innerText = "Add Patient";
    document.getElementById("patientForm").reset();
    document.getElementById("patientId").value = "";
    document.getElementById("patientModal").style.display = "flex";
}

function closePatientModal() {
    document.getElementById("patientModal").style.display = "none";
}

function fillPatient(id, name, email, phone, password, gender, dob) {
    document.getElementById("modalTitle").innerText = "Update Patient";
    document.getElementById("patientId").value = id;
    document.getElementById("patientName").value = name;
    document.getElementById("patientEmail").value = email;
    document.getElementById("patientPhone").value = phone;
    document.getElementById("patientPassword").value = password;
    document.getElementById("patientGender").value = gender;
    document.getElementById("patientDob").value = dob;
    document.getElementById("patientModal").style.display = "flex";
}

async function savePatient(e) {
    e.preventDefault();
    const id = document.getElementById("patientId").value;
    const patient = {
        fullName: document.getElementById("patientName").value,
        email: document.getElementById("patientEmail").value,
        phone: parseInt(document.getElementById("patientPhone").value),
        password: document.getElementById("patientPassword").value,
        gender: document.getElementById("patientGender").value,
        dob: document.getElementById("patientDob").value
    };
    let response;
    if (id === "") {
        response = await addPatient(patient, getToken());
    } else {
        response = await updatePatient(id, patient, getToken());
    }
    alert(response.message || "Success");
    closePatientModal();
    loadPatients();
}

async function deletePatientRecord(id) {
    if (!confirm("Delete this patient?")) {
        return;
    }
    const response = await deletePatient(id, getToken());
    alert(response.message);
    loadPatients();
}

function searchPatient() {
    const input = document.getElementById("searchPatient").value.toLowerCase();
    const rows = document.querySelectorAll("#patientTable tr");
    rows.forEach(row => {
        row.style.display = row.innerText.toLowerCase().includes(input) ? "" : "none";
    });
}