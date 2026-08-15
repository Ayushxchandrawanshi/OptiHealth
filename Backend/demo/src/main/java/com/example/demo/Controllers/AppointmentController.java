package com.example.demo.Controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.ApiResponse;
import com.example.demo.Models.AppointmentModel;
import com.example.demo.Services.AppointmentService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Create Appointment
    @PostMapping("/create")
    public AppointmentModel createAppointment(@RequestBody AppointmentModel appointment) {

        return appointmentService.createAppointment(appointment);
    }

    // Get Appointment By Id
    @GetMapping("/{appointmentId}")
    public AppointmentModel getAppointmentById(@PathVariable Long appointmentId) {

        return appointmentService.getAppointmentById(appointmentId);
    }

    // Get All Appointments
    @GetMapping("/all")
    public List<AppointmentModel> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // Update Appointment
    @PutMapping("/update/{appointmentId}")
    public AppointmentModel updateAppointment(@PathVariable Long appointmentId,
            @RequestBody AppointmentModel appointment) {

        return appointmentService.updateAppointment(appointmentId, appointment);
    }

    // Delete Appointment
    @DeleteMapping("/delete/{appointmentId}")
    public String deleteAppointment(@PathVariable Long appointmentId) {

        return appointmentService.deleteAppointment(appointmentId);
    }

    @PutMapping("/reschedule/{appointmentId}")
    public ApiResponse rescheduleAppointment(
            @PathVariable Long appointmentId,
            @RequestParam LocalDate appointmentDate,
            @RequestParam LocalTime appointmentTime) {

        appointmentService.rescheduleAppointment(
                appointmentId,
                appointmentDate,
                appointmentTime
        );

        return new ApiResponse(
                true,
                "Appointment Rescheduled Successfully"
        );
    }

}
