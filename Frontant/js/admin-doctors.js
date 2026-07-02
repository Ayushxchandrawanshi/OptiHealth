document.addEventListener("DOMContentLoaded", function () {
    requireRole("ADMIN");
    loadDoctors();
    const form = document.getElementById("doctorForm");
    if (form) {
        form.addEventListener("submit", saveDoctor);
    }
});

async function loadDoctors() {
    try {
        const doctors = await getAllDoctors(getToken());
        const table = document.getElementById("doctorTable");
        table.innerHTML = "";
        if (!doctors || doctors.length === 0) {
            table.innerHTML = `<tr><td colspan="7" style="text-align:center;">No Doctors Found</td></tr>`;
            return;
        }
        doctors.forEach(d => {
            table.innerHTML += `
<tr>
<td>${d.id}</td>
<td>${d.doctorName}</td>
<td>${d.email}</td>
<td>${d.specialization}</td>
<td>${d.experience}</td>
<td>₹${d.fee}</td>
<td>
<button class="doctor-btn" onclick="fillDoctor(${d.id},'${d.doctorName}','${d.email}','${d.password}','${d.specialization}',${d.experience},${d.fee},\`${d.description || ""}\`)">
Edit
</button>
<button class="delete-btn" onclick="deleteDoctorRecord(${d.id})">
Delete
</button>
</td>
</tr>
`;
        });
    } catch (e) {
        console.error(e);
    }
}

function openDoctorModal() {
    document.getElementById("modalTitle").innerText = "Add Doctor";
    document.getElementById("doctorForm").reset();
    document.getElementById("doctorId").value = "";
    document.getElementById("doctorModal").style.display = "flex";
}

function closeDoctorModal() {
    document.getElementById("doctorModal").style.display = "none";
}

function fillDoctor(id, name, email, password, specialization, experience, fee, description) {
    document.getElementById("modalTitle").innerText = "Update Doctor";
    document.getElementById("doctorId").value = id;
    document.getElementById("doctorName").value = name;
    document.getElementById("doctorEmail").value = email;
    document.getElementById("doctorPassword").value = password;
    document.getElementById("doctorSpecialization").value = specialization;
    document.getElementById("doctorExperience").value = experience;
    document.getElementById("doctorFee").value = fee;
    document.getElementById("doctorDescription").value = description;
    document.getElementById("doctorModal").style.display = "flex";
}

async function saveDoctor(e) {
    e.preventDefault();
    const id = document.getElementById("doctorId").value;
    const doctor = {
        doctorName: document.getElementById("doctorName").value,
        email: document.getElementById("doctorEmail").value,
        password: document.getElementById("doctorPassword").value,
        specialization: document.getElementById("doctorSpecialization").value,
        experience: parseInt(document.getElementById("doctorExperience").value),
        fee: parseFloat(document.getElementById("doctorFee").value),
        description: document.getElementById("doctorDescription").value
    };
    let response;
    if (id === "") {
        response = await addDoctor(doctor, getToken());
    } else {
        response = await updateDoctor(id, doctor, getToken());
    }
    alert(response.message || "Success");
    closeDoctorModal();
    loadDoctors();
}

async function deleteDoctorRecord(id) {
    if (!confirm("Delete this doctor?")) {
        return;
    }
    const response = await deleteDoctor(id, getToken());
    alert(response.message);
    loadDoctors();
}

function searchDoctor() {
    const input = document.getElementById("searchDoctor").value.toLowerCase();
    const rows = document.querySelectorAll("#doctorTable tr");
    rows.forEach(row => {
        row.style.display = row.innerText.toLowerCase().includes(input) ? "" : "none";
    });
}