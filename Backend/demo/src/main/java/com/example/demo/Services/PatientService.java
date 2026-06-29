package com.example.demo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        appointmentRepo.save(appointment);

    }

    // ==========================
    // View My Appointments
    // ==========================
    public List<AppointmentModel> getMyAppointments(Long patientId) {

        return appointmentRepo.findByPatientId(patientId);

    }

}
