package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Configuration.JwtUtils;
import com.example.demo.DTO.AdminDTO;
import com.example.demo.DTO.AuthResponse;
import com.example.demo.DTO.DoctorDTO;
import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.LoginResponse;
import com.example.demo.DTO.PatientDTO;
import com.example.demo.DTO.RegisterRequest;
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

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AdminService adminService;

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
                patient.getMobileNumber(),
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
                admin.getMobileNumber());

        return new LoginResponse(
                true,
                "Admin Login Successful",
                token,
                admin.getEmail(),
                "ADMIN",
                adminDTO);
    }

// ===========================================
// COMMON LOGIN
// ===========================================
    public AuthResponse login(LoginRequest request) {

        if (request.getRole() == null || request.getRole().isBlank()) {
            return new AuthResponse(
                    false,
                    "Role is required",
                    null,
                    null,
                    null,
                    null);
        }

        switch (request.getRole().toUpperCase()) {

            case "PATIENT":

                LoginResponse patientResponse = patientLogin(request);

                return new AuthResponse(
                        patientResponse.isSuccess(),
                        patientResponse.getMessage(),
                        patientResponse.getToken(),
                        patientResponse.getEmail(),
                        patientResponse.getRole(),
                        patientResponse.getData());

            case "DOCTOR":

                LoginResponse doctorResponse = doctorLogin(request);

                return new AuthResponse(
                        doctorResponse.isSuccess(),
                        doctorResponse.getMessage(),
                        doctorResponse.getToken(),
                        doctorResponse.getEmail(),
                        doctorResponse.getRole(),
                        doctorResponse.getData());

            case "ADMIN":

                LoginResponse adminResponse = adminLogin(request);

                return new AuthResponse(
                        adminResponse.isSuccess(),
                        adminResponse.getMessage(),
                        adminResponse.getToken(),
                        adminResponse.getEmail(),
                        adminResponse.getRole(),
                        adminResponse.getData());

            default:

                return new AuthResponse(
                        false,
                        "Invalid Role",
                        null,
                        null,
                        null,
                        null);

        }
    }

    // ===========================================
// COMMON REGISTER
// ===========================================
    public AuthResponse register(RegisterRequest request) {

        if (request.getRole() == null || request.getRole().isBlank()) {
            return new AuthResponse(false,
                    "Role is required",
                    null,
                    null,
                    null,
                    null);
        }

        switch (request.getRole().toUpperCase()) {

            case "PATIENT":

                PatientModel patient = new PatientModel();

                patient.setFullName(request.getFullName());
                patient.setEmail(request.getEmail());
                patient.setPassword(request.getPassword());
                patient.setMobileNumber(request.getMobileNumber());
                patient.setGender(request.getGender());
                patient.setDob(request.getDob());

                patientService.registerPatient(patient);

                return new AuthResponse(
                        true,
                        "Patient Registered Successfully",
                        null,
                        patient.getEmail(),
                        "PATIENT",
                        patient);

            case "DOCTOR":

                DoctorModel doctor = new DoctorModel();

                doctor.setDoctorName(request.getDoctorName());
                doctor.setEmail(request.getEmail());
                doctor.setPassword(request.getPassword());
                doctor.setSpecialization(request.getSpecialization());
                doctor.setExperience(request.getExperience());
                doctor.setFee(request.getFee());
                doctor.setDescription(request.getDescription());

                doctorService.registerDoctor(doctor);

                return new AuthResponse(
                        true,
                        "Doctor Registered Successfully",
                        null,
                        doctor.getEmail(),
                        "DOCTOR",
                        doctor);

            case "ADMIN":

                AdminModel admin = new AdminModel();

                admin.setFullName(request.getFullName());
                admin.setEmail(request.getEmail());
                admin.setPassword(request.getPassword());
                admin.setMobileNumber(request.getMobileNumber());

                adminService.registerAdmin(admin);

                return new AuthResponse(
                        true,
                        "Admin Registered Successfully",
                        null,
                        admin.getEmail(),
                        "ADMIN",
                        admin);

            default:

                return new AuthResponse(
                        false,
                        "Invalid Role",
                        null,
                        null,
                        null,
                        null);
        }

    }

// ===========================================
// FORGOT PASSWORD
// ===========================================
    public AuthResponse forgotPassword(String email) {

        return new AuthResponse(
                true,
                "Forgot Password API is under development.",
                null,
                email,
                null,
                null);
    }

// ===========================================
// VERIFY OTP
// ===========================================
    public AuthResponse verifyOtp(String email, String otp) {

        return new AuthResponse(
                true,
                "OTP Verification API is under development.",
                null,
                email,
                null,
                null);
    }

// ===========================================
// RESET PASSWORD
// ===========================================
    public AuthResponse resetPassword(String email, String newPassword) {

        return new AuthResponse(
                true,
                "Reset Password API is under development.",
                null,
                email,
                null,
                null);
    }
}
