const BASE_URL = "http://localhost:8080/api";

async function apiRequest(endpoint, method = "GET", body = null, token = null) {

    const options = {
        method: method,
        headers: {
            "Content-Type": "application/json"
        }
    };

    if (body) {
        options.body = JSON.stringify(body);
    }

    if (token) {
        options.headers.Authorization = `Bearer ${token}`;
    }

    try {

        const response = await fetch(BASE_URL + endpoint, options);

        const data = await response.json();

        if (!response.ok) {
            return {
                success: false,
                message: data.message || "Request Failed"
            };
        }

        return data;

    } catch (error) {

        console.error("API ERROR :", error);

        return {
            success: false,
            message: "Unable to connect to server."
        };

    }

}

/* ===========================
   PATIENT APIs
=========================== */

function patientRegister(patient) {
    return apiRequest("/patients/register", "POST", patient);
}

function patientLogin(login) {
    return apiRequest("/patients/login", "POST", login);
}

function getDoctors(token = null) {
    return apiRequest("/patients/doctors", "GET", null, token);
}

function bookAppointment(appointment, token) {
    return apiRequest("/patients/bookAppointment", "POST", appointment, token);
}

function getPatientAppointments(patientId, token) {
    return apiRequest(`/patients/appointments/${patientId}`, "GET", null, token);
}

/* ===========================
   DOCTOR APIs
=========================== */

function doctorRegister(doctor) {
    return apiRequest("/doctors/register", "POST", doctor);
}

function doctorLogin(login) {
    return apiRequest("/doctors/login", "POST", login);
}

function getDoctorAppointments(doctorId, token) {
    return apiRequest(`/doctors/appointments/${doctorId}`, "GET", null, token);
}

function acceptAppointment(id, date, time, token) {
    return apiRequest(`/doctors/accept/${id}?appointmentDate=${date}&appointmentTime=${time}`, "PUT", null, token);
}

function rejectAppointment(id, token) {
    return apiRequest(`/doctors/reject/${id}`, "PUT", null, token);
}

/* ===========================
   APPOINTMENT APIs
=========================== */

function createAppointment(appointment, token) {
    return apiRequest("/appointment/create", "POST", appointment, token);
}

function getAppointment(id, token) {
    return apiRequest(`/appointment/${id}`, "GET", null, token);
}

function getAllAppointments(token) {
    return apiRequest("/appointment/all", "GET", null, token);
}

function updateAppointment(id, appointment, token) {
    return apiRequest(`/appointment/update/${id}`, "PUT", appointment, token);
}

function deleteAppointment(id, token) {
    return apiRequest(`/appointment/delete/${id}`, "DELETE", null, token);
}

/* ===========================
   ADMIN APIs
=========================== */

function adminRegister(admin) {
    return apiRequest("/admin/register", "POST", admin);
}

function adminLogin(login) {
    return apiRequest("/admin/login", "POST", login);
}

function getAllDoctors(token) {
    return apiRequest("/admin/doctor", "GET", null, token);
}

function getAllPatients(token) {
    return apiRequest("/admin/patient", "GET", null, token);
}

function getAdminAppointments(token) {
    return apiRequest("/admin/appointment", "GET", null, token);
}