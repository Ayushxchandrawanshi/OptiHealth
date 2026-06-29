package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.AppointmentModel;
import com.example.demo.Repository.AppointmentRepo;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepo;

    // Create Appointment
    public AppointmentModel createAppointment(AppointmentModel appointment) {
        return appointmentRepo.save(appointment);
    }

    // Get Appointment By Id
    public AppointmentModel getAppointmentById(Long appointmentId) {

        Optional<AppointmentModel> optionalAppointment
                = appointmentRepo.findById(appointmentId);

        if (optionalAppointment.isPresent()) {
            return optionalAppointment.get();
        }

        return null;
    }

    // Get All Appointments
    public List<AppointmentModel> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    // Update Appointment
    public AppointmentModel updateAppointment(Long appointmentId, AppointmentModel appointment) {

        Optional<AppointmentModel> optionalAppointment
                = appointmentRepo.findById(appointmentId);

        if (optionalAppointment.isPresent()) {

            AppointmentModel existingAppointment = optionalAppointment.get();

            existingAppointment.setDoctorId(appointment.getDoctorId());
            existingAppointment.setPatientId(appointment.getPatientId());
            existingAppointment.setBookingDate(appointment.getBookingDate());
            existingAppointment.setAppointmentDate(appointment.getAppointmentDate());
            existingAppointment.setAppointmentTime(appointment.getAppointmentTime());
            existingAppointment.setStatus(appointment.getStatus());
            existingAppointment.setReason(appointment.getReason());
            existingAppointment.setRemarks(appointment.getRemarks());

            return appointmentRepo.save(existingAppointment);
        }

        return null;
    }

    // Delete Appointment
    public String deleteAppointment(Long appointmentId) {

        appointmentRepo.deleteById(appointmentId);

        return "Appointment Deleted Successfully";
    }

}
