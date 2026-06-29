package com.example.demo.Controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.ApiResponse;
import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.LoginResponse;
import com.example.demo.Models.AppointmentModel;
import com.example.demo.Models.DoctorModel;
import com.example.demo.Services.AuthService;
import com.example.demo.Services.DoctorService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AuthService authService;

    // ==========================
    // Register Doctor
    // ==========================
    @PostMapping("/register")
    public ApiResponse registerDoctor(@RequestBody DoctorModel doctor) {

        doctorService.registerDoctor(doctor);

        return new ApiResponse(
                true,
                "Doctor Registered Successfully");

    }

    // ==========================
    // Doctor Login
    // ==========================
    @PostMapping("/login")
    public LoginResponse loginDoctor(@RequestBody LoginRequest request) {

        return authService.doctorLogin(request);

    }

    // ==========================
    // View Doctor Appointments
    // ==========================
    @GetMapping("/appointments/{doctorId}")
    public List<AppointmentModel> getDoctorAppointments(
            @PathVariable Long doctorId) {

        return doctorService.getDoctorAppointments(doctorId);

    }

    // ==========================
    // Accept Appointment
    // ==========================
    @PutMapping("/accept/{appointmentId}")
    public ApiResponse acceptAppointment(
            @PathVariable Long appointmentId,
            @RequestParam LocalDate appointmentDate,
            @RequestParam LocalTime appointmentTime) {

        doctorService.acceptAppointment(
                appointmentId,
                appointmentDate,
                appointmentTime);

        return new ApiResponse(
                true,
                "Appointment Accepted Successfully");
    }

    // ==========================
    // Reject Appointment
    // ==========================
    @PutMapping("/reject/{appointmentId}")
    public ApiResponse rejectAppointment(@PathVariable Long appointmentId) {

        doctorService.rejectAppointment(appointmentId);

        return new ApiResponse(
                true,
                "Appointment Rejected Successfully");

    }

}
