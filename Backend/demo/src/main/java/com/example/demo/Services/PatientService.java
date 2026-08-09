package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.PatientDashboardDTO;
import com.example.demo.Models.AppointmentModel;
import com.example.demo.Models.DoctorModel;
import com.example.demo.Models.PatientModel;
import com.example.demo.Repository.AppointmentRepo;
import com.example.demo.Repository.DoctorRepo;
import com.example.demo.Repository.PatientRepo;

@Service
public class PatientService {

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ==========================
    // Register Patient
    // ==========================
    public void registerPatient(PatientModel patient) {
        patient.setPassword(
                passwordEncoder.encode(patient.getPassword()));
        patientRepo.save(patient);
    }

    // ==========================
    // Get All Doctors
    // ==========================
    public List<DoctorModel> getAllDoctors() {
        return doctorRepo.findAll();
    }

    // ==========================
    // Book Appointment
    // ==========================
    public void bookAppointment(AppointmentModel appointment) {
        appointment.setStatus("PENDING");
        appointmentRepo.save(appointment);
    }

    // ==========================
    // View My Appointments
    // ==========================
    public List<AppointmentModel> getMyAppointments(Long patientId) {
        return appointmentRepo.findByPatientId(patientId);
    }

    // ==========================
// Patient Dashboard
// ==========================
    public PatientDashboardDTO getDashboardData(Long patientId) {
        Optional<PatientModel> optionalPatient = patientRepo.findById(patientId);

        if (optionalPatient.isEmpty()) {
            return null;
        }

        PatientModel patient = optionalPatient.get();
        List<AppointmentModel> appointments = appointmentRepo.findByPatientId(patientId);
        long totalDoctors = doctorRepo.count();
        String nextDoctor = "Not Assigned";

        if (!appointments.isEmpty()) {
            AppointmentModel appointment = appointments.get(0);
            DoctorModel doctor = doctorRepo.findById(appointment.getDoctorId()).orElse(null);

            if (doctor != null) {
                nextDoctor = doctor.getDoctorName();
            }
        }

        PatientDashboardDTO dashboard = new PatientDashboardDTO();
        dashboard.setFullName(patient.getFullName());
        dashboard.setEmail(patient.getEmail());
        dashboard.setMobileNumber(patient.getMobileNumber());
        dashboard.setAppointmentCount(appointments.size());
        dashboard.setDoctorCount((int) totalDoctors);
        dashboard.setReportCount(0);
        dashboard.setPrescriptionCount(0);
        dashboard.setNextDoctorName(nextDoctor);
        return dashboard;
    }

    public PatientModel getPatientById(Long id) {
        return patientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    public PatientModel updatePatient(Long id, PatientModel updatedPatient) {
        PatientModel patient = patientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        patient.setFullName(updatedPatient.getFullName());
        patient.setEmail(updatedPatient.getEmail());
        patient.setMobileNumber(updatedPatient.getMobileNumber());
        patient.setGender(updatedPatient.getGender());
        patient.setDob(updatedPatient.getDob());
        patient.setBloodGroup(updatedPatient.getBloodGroup());
        patient.setAddress(updatedPatient.getAddress());
        patient.setCity(updatedPatient.getCity());
        patient.setState(updatedPatient.getState());
        patient.setPincode(updatedPatient.getPincode());
        patient.setEmergencyContact(updatedPatient.getEmergencyContact());
        patient.setHeight(updatedPatient.getHeight());
        patient.setWeight(updatedPatient.getWeight());
        patient.setAllergies(updatedPatient.getAllergies());
        patient.setExistingDiseases(updatedPatient.getExistingDiseases());
        patient.setCurrentMedications(updatedPatient.getCurrentMedications());
        return patientRepo.save(patient);
    }

}
