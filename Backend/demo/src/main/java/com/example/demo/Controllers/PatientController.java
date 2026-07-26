package com.example.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.ApiResponse;
import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.LoginResponse;
import com.example.demo.Models.AppointmentModel;
import com.example.demo.Models.DoctorModel;
import com.example.demo.Models.PatientModel;
import com.example.demo.Services.AuthService;
import com.example.demo.Services.PatientService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private AuthService authService;

    // ==========================
    // Register Patient
    // ==========================
    @PostMapping("/register")
    public ApiResponse registerPatient(@RequestBody PatientModel patient) {
        patientService.registerPatient(patient);
        return new ApiResponse(
                true,
                "Patient Registered Successfully");

    }

    // ==========================
    // Patient Login
    // ==========================
    @PostMapping("/login")
    public LoginResponse loginPatient(@RequestBody LoginRequest request) {
        return authService.patientLogin(request);
    }

    // ==========================
    // View All Doctors
    // ==========================
    @GetMapping("/doctors")
    public List<DoctorModel> getAllDoctors() {
        return patientService.getAllDoctors();
    }

    // ==========================
    // Book Appointment
    // ==========================
    @PostMapping("/bookAppointment")
    public ApiResponse bookAppointment(@RequestBody AppointmentModel appointment) {

        patientService.bookAppointment(appointment);

        return new ApiResponse(
                true,
                "Appointment Booked Successfully");
    }

    // ==========================
    // View My Appointments
    // ==========================
    @GetMapping("/appointments/{patientId}")
    public List<AppointmentModel> getMyAppointments(@PathVariable Long patientId) {

        return patientService.getMyAppointments(patientId);

    }

}
