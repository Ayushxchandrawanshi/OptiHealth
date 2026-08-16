package com.example.demo.Controllers;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/register")
    public ApiResponse registerAdmin(
            @RequestBody AdminModel admin) {

        adminService.registerAdmin(admin);

        return new ApiResponse(
                true,
                "Admin Registered Successfully");
    }

    @PostMapping("/login")
    public LoginResponse loginAdmin(
            @RequestBody LoginRequest request) {

        return authService.adminLogin(request);
    }

    @GetMapping("/{adminId}")
    public AdminModel getAdminById(
            @PathVariable Long adminId) {

        return adminService.getAdminById(
                adminId);
    }

    @GetMapping("/email/{email}")
    public AdminModel getAdminByEmail(
            @PathVariable String email) {

        return adminService.getAdminByEmail(
                email);
    }

    @PutMapping("/{adminId}")
    public AdminModel updateAdmin(
            @PathVariable Long adminId,
            @RequestBody AdminModel admin) {

        return adminService.updateAdmin(
                adminId,
                admin);
    }

    @DeleteMapping("/{adminId}")
    public ApiResponse deleteAdmin(
            @PathVariable Long adminId) {

        adminService.deleteAdmin(
                adminId);

        return new ApiResponse(
                true,
                "Admin Deleted Successfully");
    }

    @PostMapping("/doctor")
    public ApiResponse addDoctor(
            @RequestBody DoctorModel doctor) {

        adminService.addDoctor(doctor);

        return new ApiResponse(
                true,
                "Doctor Added Successfully");
    }

    @GetMapping("/doctor")
    public List<DoctorModel> getAllDoctors() {

        return adminService.getAllDoctors();
    }

    @GetMapping("/doctor/{doctorId}")
    public DoctorModel getDoctorById(
            @PathVariable Long doctorId) {

        return adminService.getDoctorById(
                doctorId);
    }

    @PutMapping("/doctor/{doctorId}")
    public DoctorModel updateDoctor(
            @PathVariable Long doctorId,
            @RequestBody DoctorModel doctor) {

        return adminService.updateDoctor(
                doctorId,
                doctor);
    }

    @DeleteMapping("/doctor/{doctorId}")
    public ApiResponse deleteDoctor(
            @PathVariable Long doctorId) {

        adminService.deleteDoctor(
                doctorId);

        return new ApiResponse(
                true,
                "Doctor Deleted Successfully");
    }

    @PostMapping("/patient")
    public ApiResponse addPatient(
            @RequestBody PatientModel patient) {

        adminService.addPatient(patient);

        return new ApiResponse(
                true,
                "Patient Added Successfully");
    }

    @GetMapping("/patient")
    public List<PatientModel> getAllPatients() {

        return adminService.getAllPatients();
    }

    @GetMapping("/patient/{patientId}")
    public PatientModel getPatientById(
            @PathVariable Long patientId) {

        return adminService.getPatientById(
                patientId);
    }

    @PutMapping("/patient/{patientId}")
    public PatientModel updatePatient(
            @PathVariable Long patientId,
            @RequestBody PatientModel patient) {

        return adminService.updatePatient(
                patientId,
                patient);
    }

    @DeleteMapping("/patient/{patientId}")
    public ApiResponse deletePatient(
            @PathVariable Long patientId) {

        adminService.deletePatient(
                patientId);

        return new ApiResponse(
                true,
                "Patient Deleted Successfully");
    }

    @GetMapping("/appointment")
    public List<AppointmentModel> getAllAppointments() {

        return adminService.getAllAppointments();
    }

    @GetMapping("/appointment/{appointmentId}")
    public AppointmentModel getAppointmentById(
            @PathVariable Long appointmentId) {

        return adminService.getAppointmentById(
                appointmentId);
    }

    @DeleteMapping("/appointment/{appointmentId}")
    public ApiResponse deleteAppointment(
            @PathVariable Long appointmentId) {

        adminService.deleteAppointment(
                appointmentId);

        return new ApiResponse(
                true,
                "Appointment Deleted Successfully");
    }

    @PostMapping(
            value = "/{adminId}/profile-image",
            consumes = "multipart/form-data"
    )
    public AdminModel uploadProfileImage(
            @PathVariable Long adminId,
            @RequestPart("file") MultipartFile file)
            throws IOException {

        return adminService.uploadProfileImage(
                adminId,
                file
        );
    }
}
