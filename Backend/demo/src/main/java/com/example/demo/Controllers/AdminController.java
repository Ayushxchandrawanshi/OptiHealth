package com.example.demo.Controllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.ApiResponse;
import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.LoginResponse;
import com.example.demo.Models.AdminModel;
import com.example.demo.Models.AppointmentModel;
import com.example.demo.Models.DoctorModel;
import com.example.demo.Models.PatientModel;
import com.example.demo.Services.AdminService;
import com.example.demo.Services.AuthService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthService authService;

    // ===========================
    // Admin Login
    // ===========================
    @PostMapping("/register")
    public ApiResponse registerAdmin(@RequestBody AdminModel admin) {

        adminService.registerAdmin(admin);

        return new ApiResponse(
                true,
                "Admin Registered Successfully");
    }

    @PostMapping("/login")
    public LoginResponse loginAdmin(@RequestBody LoginRequest request) {

        return authService.adminLogin(request);

    }

    // ===========================
    // Doctor CRUD
    // ===========================
    // Add Doctor
    @PostMapping("/doctor")
    public ApiResponse addDoctor(@RequestBody DoctorModel doctor) {

        adminService.addDoctor(doctor);

        return new ApiResponse(
                true,
                "Doctor Added Successfully");

    }

    // View All Doctors
    @GetMapping("/doctor")
    public List<DoctorModel> getAllDoctors() {
        return adminService.getAllDoctors();
    }

    // View Doctor By Id
    @GetMapping("/doctor/{doctorId}")
    public DoctorModel getDoctorById(@PathVariable Long doctorId) {
        return adminService.getDoctorById(doctorId);
    }

    // Update Doctor
    @PutMapping("/doctor/{doctorId}")
    public DoctorModel updateDoctor(
            @PathVariable Long doctorId,
            @RequestBody DoctorModel doctor) {

        return adminService.updateDoctor(doctorId, doctor);
    }

    // Delete Doctor
    @DeleteMapping("/doctor/{doctorId}")
    public ApiResponse deleteDoctor(@PathVariable Long doctorId) {

        adminService.deleteDoctor(doctorId);

        return new ApiResponse(
                true,
                "Doctor Deleted Successfully");
    }

    // ===========================
    // Patient CRUD
    // ===========================
    // Add Patient
    @PostMapping("/patient")
    public ApiResponse addPatient(@RequestBody PatientModel patient) {

        adminService.addPatient(patient);

        return new ApiResponse(
                true,
                "Patient Added Successfully");

    }

    // View All Patients
    @GetMapping("/patient")
    public List<PatientModel> getAllPatients() {
        return adminService.getAllPatients();
    }

    // View Patient By Id
    @GetMapping("/patient/{patientId}")
    public PatientModel getPatientById(@PathVariable Long patientId) {
        return adminService.getPatientById(patientId);
    }

    // Update Patient
    @PutMapping("/patient/{patientId}")
    public PatientModel updatePatient(
            @PathVariable Long patientId,
            @RequestBody PatientModel patient) {

        return adminService.updatePatient(patientId, patient);
    }

    // Delete Patient
    @DeleteMapping("/patient/{patientId}")
    public ApiResponse deletePatient(@PathVariable Long patientId) {

        adminService.deletePatient(patientId);

        return new ApiResponse(
                true,
                "Patient Deleted Successfully");
    }

    // ===========================
    // Appointment
    // ===========================
    // View All Appointments
    @GetMapping("/appointment")
    public List<AppointmentModel> getAllAppointments() {
        return adminService.getAllAppointments();
    }

    // View Appointment By Id
    @GetMapping("/appointment/{appointmentId}")
    public AppointmentModel getAppointmentById(
            @PathVariable Long appointmentId) {

        return adminService.getAppointmentById(appointmentId);
    }

    // Delete Appointment
    @DeleteMapping("/appointment/{appointmentId}")
    public ApiResponse deleteAppointment(@PathVariable Long appointmentId) {

        adminService.deleteAppointment(appointmentId);

        return new ApiResponse(
                true,
                "Appointment Deleted Successfully");
    }

}
