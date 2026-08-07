package com.example.demo.Services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Models.AppointmentModel;
import com.example.demo.Models.DoctorModel;
import com.example.demo.Repository.AppointmentRepo;
import com.example.demo.Repository.DoctorRepo;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ==========================
    // Register Doctor
    // ==========================
    public void registerDoctor(DoctorModel doctor) {

        doctor.setPassword(
                passwordEncoder.encode(doctor.getPassword()));

        doctorRepo.save(doctor);

    }

    // ==========================
    // Get Doctor Appointments
    // ==========================
    public List<AppointmentModel> getDoctorAppointments(Long doctorId) {

        return appointmentRepo.findByDoctorId(doctorId);

    }

    // ==========================
    // Accept Appointment
    // ==========================
    public void acceptAppointment(
            Long appointmentId,
            LocalDate appointmentDate,
            LocalTime appointmentTime) {

        Optional<AppointmentModel> optionalAppointment
                = appointmentRepo.findById(appointmentId);

        if (optionalAppointment.isPresent()) {

            AppointmentModel appointment = optionalAppointment.get();

            appointment.setAppointmentDate(appointmentDate);
            appointment.setAppointmentTime(appointmentTime);
            appointment.setStatus("ACCEPTED");

            appointmentRepo.save(appointment);
        }

    }

    // ==========================
    // Reject Appointment
    // ==========================
    public void rejectAppointment(Long appointmentId) {

        Optional<AppointmentModel> optionalAppointment
                = appointmentRepo.findById(appointmentId);

        if (optionalAppointment.isPresent()) {

            AppointmentModel appointment = optionalAppointment.get();

            appointment.setStatus("REJECTED");

            appointmentRepo.save(appointment);
        }

    }

    // ==========================
// Get Doctor By Id
// ==========================
    public DoctorModel getDoctorById(Long doctorId) {

        return doctorRepo.findById(doctorId).orElse(null);

    }

}
