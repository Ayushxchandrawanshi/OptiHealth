package com.example.demo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Models.AdminModel;
import com.example.demo.Models.DoctorModel;
import com.example.demo.Models.PatientModel;
import com.example.demo.Repository.AdminRepo;
import com.example.demo.Repository.DoctorRepo;
import com.example.demo.Repository.PatientRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // ===========================
        // Check Admin
        // ===========================
        AdminModel admin = adminRepo.findByEmail(email);

        if (admin != null) {
            return new CustomUserDetails(
                    admin.getEmail(),
                    admin.getPassword(),
                    "ADMIN");
        }

        // ===========================
        // Check Doctor
        // ===========================
        DoctorModel doctor = doctorRepo.findByEmail(email);

        if (doctor != null) {
            return new CustomUserDetails(
                    doctor.getEmail(),
                    doctor.getPassword(),
                    "DOCTOR");
        }

        // ===========================
        // Check Patient
        // ===========================
        PatientModel patient = patientRepo.findByEmail(email);

        if (patient != null) {
            return new CustomUserDetails(
                    patient.getEmail(),
                    patient.getPassword(),
                    "PATIENT");
        }

        throw new UsernameNotFoundException("User not found with email : " + email);
    }

}
