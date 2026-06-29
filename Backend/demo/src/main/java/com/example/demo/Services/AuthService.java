package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Configuration.JwtUtils;
import com.example.demo.DTO.AdminDTO;
import com.example.demo.DTO.DoctorDTO;
import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.LoginResponse;
import com.example.demo.DTO.PatientDTO;
import com.example.demo.Models.AdminModel;
import com.example.demo.Models.DoctorModel;
import com.example.demo.Models.PatientModel;
import com.example.demo.Repository.AdminRepo;
import com.example.demo.Repository.DoctorRepo;
import com.example.demo.Repository.PatientRepo;

@Service
public class AuthService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ===========================================
    // PATIENT LOGIN
    // ===========================================
    public LoginResponse patientLogin(LoginRequest request) {

        PatientModel patient = patientRepo.findByEmail(request.getEmail());

        if (patient == null) {
            return new LoginResponse(false, "Patient Not Found",
                    null, null, null, null);
        }

        if (!passwordEncoder.matches(request.getPassword(), patient.getPassword())) {
            return new LoginResponse(false, "Invalid Password",
                    null, null, null, null);
        }

        String token = jwtUtils.generateToken(patient.getEmail(), "PATIENT");

        PatientDTO patientDTO = new PatientDTO(
                patient.getId(),
                patient.getFullName(),
                patient.getEmail(),
                patient.getPhone(),
                patient.getGender(),
                patient.getDob());

        return new LoginResponse(
                true,
                "Patient Login Successful",
                token,
                patient.getEmail(),
                "PATIENT",
                patientDTO);
    }

    // ===========================================
    // DOCTOR LOGIN
    // ===========================================
    public LoginResponse doctorLogin(LoginRequest request) {

        DoctorModel doctor = doctorRepo.findByEmail(request.getEmail());

        if (doctor == null) {
            return new LoginResponse(false, "Doctor Not Found",
                    null, null, null, null);
        }

        if (!passwordEncoder.matches(request.getPassword(), doctor.getPassword())) {
            return new LoginResponse(false, "Invalid Password",
                    null, null, null, null);
        }

        String token = jwtUtils.generateToken(doctor.getEmail(), "DOCTOR");

        DoctorDTO doctorDTO = new DoctorDTO(
                doctor.getId(),
                doctor.getDoctorName(),
                doctor.getEmail(),
                doctor.getSpecialization(),
                doctor.getExperience(),
                doctor.getFee(),
                doctor.getDescription());

        return new LoginResponse(
                true,
                "Doctor Login Successful",
                token,
                doctor.getEmail(),
                "DOCTOR",
                doctorDTO);
    }

    // ===========================================
    // ADMIN LOGIN
    // ===========================================
    public LoginResponse adminLogin(LoginRequest request) {

        AdminModel admin = adminRepo.findByEmail(request.getEmail());

        if (admin == null) {
            return new LoginResponse(false, "Admin Not Found",
                    null, null, null, null);
        }

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            return new LoginResponse(false, "Invalid Password",
                    null, null, null, null);
        }

        String token = jwtUtils.generateToken(admin.getEmail(), "ADMIN");

        AdminDTO adminDTO = new AdminDTO(
                admin.getAdminId(),
                admin.getFullName(),
                admin.getEmail(),
                admin.getPhoneNumber());

        return new LoginResponse(
                true,
                "Admin Login Successful",
                token,
                admin.getEmail(),
                "ADMIN",
                adminDTO);
    }

}
