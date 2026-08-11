package com.example.demo.Services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.DoctorAppointmentDTO;
import com.example.demo.Models.AppointmentModel;
import com.example.demo.Models.DoctorModel;
import com.example.demo.Models.PatientModel;
import com.example.demo.Repository.AppointmentRepo;
import com.example.demo.Repository.DoctorRepo;
import com.example.demo.Repository.PatientRepo;

@Service

public class DoctorService {

    @Autowired
    private DoctorRepo doctorRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PatientRepo patientRepo;

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
    public List<DoctorAppointmentDTO> getDoctorAppointments(Long doctorId) {
        List<AppointmentModel> appointments
                = appointmentRepo.findByDoctorId(doctorId);
        List<DoctorAppointmentDTO> result = new ArrayList<>();
        for (AppointmentModel appointment : appointments) {
            PatientModel patient
                    = patientRepo.findById(appointment.getPatientId())
                            .orElse(null);
            DoctorAppointmentDTO dto = new DoctorAppointmentDTO();
            dto.setAppointmentId(appointment.getAppointmentId());
            dto.setPatientId(appointment.getPatientId());
            dto.setAppointmentDate(appointment.getAppointmentDate());
            dto.setAppointmentTime(appointment.getAppointmentTime());
            dto.setStatus(appointment.getStatus());
            dto.setReason(appointment.getReason());
            dto.setRemarks(appointment.getRemarks());
            if (patient != null) {
                dto.setPatientName(patient.getFullName());
                dto.setPatientEmail(patient.getEmail());
                dto.setMobileNumber(patient.getMobileNumber());
            } else {
                dto.setPatientName("Patient Not Found");
                dto.setPatientEmail("-");
                dto.setMobileNumber(null);
            }
            result.add(dto);
        }
        return result;
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
    // Complete Appointment
    // ==========================
    public void completeAppointment(Long appointmentId) {
        Optional<AppointmentModel> optionalAppointment
                = appointmentRepo.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            AppointmentModel appointment
                    = optionalAppointment.get();
            appointment.setStatus("COMPLETED");
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
