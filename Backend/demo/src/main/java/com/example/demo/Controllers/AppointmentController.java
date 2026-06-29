package com.example.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}