package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Models.AdminModel;
import com.example.demo.Models.AppointmentModel;
import com.example.demo.Models.DoctorModel;
import com.example.demo.Models.PatientModel;
import com.example.demo.Repository.AdminRepo;
import com.example.demo.Repository.AppointmentRepo;
import com.example.demo.Repository.DoctorRepo;
import com.example.demo.Repository.PatientRepo;

@Service
public class AdminService {

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerAdmin(AdminModel admin) {

        admin.setPassword(
                passwordEncoder.encode(admin.getPassword()));

        adminRepo.save(admin);
    }

    // ===========================
    // Doctor CRUD
    // ===========================
    // Add Doctor
    public void addDoctor(DoctorModel doctor) {

        doctor.setPassword(
                passwordEncoder.encode(doctor.getPassword()));

        doctorRepo.save(doctor);
    }

    // View All Doctors
    public List<DoctorModel> getAllDoctors() {

        return doctorRepo.findAll();

    }

    // View Doctor By Id
    public DoctorModel getDoctorById(Long doctorId) {

        Optional<DoctorModel> doctor = doctorRepo.findById(doctorId);

        return doctor.orElse(null);
    }

    // Update Doctor
    public DoctorModel updateDoctor(Long doctorId, DoctorModel doctor) {

        Optional<DoctorModel> optionalDoctor = doctorRepo.findById(doctorId);

        if (optionalDoctor.isPresent()) {

            DoctorModel existingDoctor = optionalDoctor.get();

            existingDoctor.setDoctorName(doctor.getDoctorName());
            existingDoctor.setEmail(doctor.getEmail());

            existingDoctor.setPassword(
                    passwordEncoder.encode(doctor.getPassword()));

            existingDoctor.setSpecialization(doctor.getSpecialization());
            existingDoctor.setExperience(doctor.getExperience());
            existingDoctor.setFee(doctor.getFee());
            existingDoctor.setDescription(doctor.getDescription());

            return doctorRepo.save(existingDoctor);
        }

        return null;
    }

    // Delete Doctor
    public void deleteDoctor(Long doctorId) {

        doctorRepo.deleteById(doctorId);

    }

    // ===========================
    // Patient CRUD
    // ===========================
    // Add Patient
    public void addPatient(PatientModel patient) {

        patient.setPassword(
                passwordEncoder.encode(patient.getPassword()));

        patientRepo.save(patient);
    }

    // View All Patients
    public List<PatientModel> getAllPatients() {

        return patientRepo.findAll();

    }

    // View Patient By Id
    public PatientModel getPatientById(Long patientId) {

        Optional<PatientModel> patient = patientRepo.findById(patientId);

        return patient.orElse(null);
    }

    // Update Patient
    public PatientModel updatePatient(Long patientId, PatientModel patient) {

        Optional<PatientModel> optionalPatient = patientRepo.findById(patientId);

        if (optionalPatient.isPresent()) {

            PatientModel existingPatient = optionalPatient.get();

            existingPatient.setFullName(patient.getFullName());
            existingPatient.setEmail(patient.getEmail());
            existingPatient.setMobileNumber(patient.getMobileNumber());

            existingPatient.setPassword(
                    passwordEncoder.encode(patient.getPassword()));

            existingPatient.setGender(patient.getGender());
            existingPatient.setDob(patient.getDob());

            return patientRepo.save(existingPatient);
        }

        return null;
    }

    // Delete Patient
    public void deletePatient(Long patientId) {

        patientRepo.deleteById(patientId);

    }

    // ===========================
    // Appointment
    // ===========================
    // View All Appointments
    public List<AppointmentModel> getAllAppointments() {

        return appointmentRepo.findAll();

    }

    // View Appointment By Id
    public AppointmentModel getAppointmentById(Long appointmentId) {

        Optional<AppointmentModel> appointment
                = appointmentRepo.findById(appointmentId);

        return appointment.orElse(null);
    }

    // Delete Appointment
    public void deleteAppointment(Long appointmentId) {

        appointmentRepo.deleteById(appointmentId);

    }

}
