const BASE_URL = "http://localhost:8080/api";

async function apiRequest(endpoint, method = "GET", body = null, token = null) {

    const options = {
        method,
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

        return data;

    } catch (error) {

        console.error(error);

        return {
            success: false,
            message: "Server Error"
        };

    }

}

/* Patient */

function patientRegister(patient) {
    return apiRequest("/patients/register", "POST", patient);
}

function patientLogin(login) {
    return apiRequest("/patients/login", "POST", login);
}

function getDoctors() {
    return apiRequest("/patients/doctors");
}

function bookAppointment(appointment, token) {
    return apiRequest("/patients/bookAppointment", "POST", appointment, token);
}

function getPatientAppointments(id, token) {
    return apiRequest(`/patients/appointments/${id}`, "GET", null, token);
}

/* Doctor */

function doctorRegister(doctor) {
    return apiRequest("/doctors/register", "POST", doctor);
}

function doctorLogin(login) {
    return apiRequest("/doctors/login", "POST", login);
}

function getDoctorAppointments(id, token) {
    return apiRequest(`/doctors/appointments/${id}`, "GET", null, token);
}

function acceptAppointment(id, date, time, token) {
    return apiRequest(`/doctors/accept/${id}?appointmentDate=${date}&appointmentTime=${time}`, "PUT", null, token);
}

function rejectAppointment(id, token) {
    return apiRequest(`/doctors/reject/${id}`, "PUT", null, token);
}

/* Admin */

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

function getAllAppointments(token) {
    return apiRequest("/admin/appointment", "GET", null, token);
}