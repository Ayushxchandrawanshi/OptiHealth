package com.example.demo.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Models.AdminModel;
import com.example.demo.Models.AppointmentModel;
import com.example.demo.Models.DoctorModel;
import com.example.demo.Models.PatientModel;
import com.example.demo.Repository.AdminRepo;
import com.example.demo.Repository.AppointmentRepo;
import com.example.demo.Repository.DoctorRepo;
import com.example.demo.Repository.PatientRepo;

@Service
public class AdminService {

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerAdmin(AdminModel admin) {

        admin.setPassword(
                passwordEncoder.encode(
                        admin.getPassword()));

        adminRepo.save(admin);
    }

    public AdminModel getAdminById(Long adminId) {

        Optional<AdminModel> admin
                = adminRepo.findById(adminId);

        return admin.orElse(null);
    }

    public AdminModel getAdminByEmail(String email) {

        return adminRepo.findByEmail(email);
    }

    public AdminModel updateAdmin(
            Long adminId,
            AdminModel admin) {

        Optional<AdminModel> optionalAdmin
                = adminRepo.findById(adminId);

        if (optionalAdmin.isPresent()) {

            AdminModel existingAdmin
                    = optionalAdmin.get();

            if (admin.getFullName() != null) {

                existingAdmin.setFullName(
                        admin.getFullName());
            }

            if (admin.getEmail() != null) {

                existingAdmin.setEmail(
                        admin.getEmail());
            }

            if (admin.getMobileNumber() != null) {

                existingAdmin.setMobileNumber(
                        admin.getMobileNumber());
            }

            if (admin.getDob() != null) {

                existingAdmin.setDob(
                        admin.getDob());
            }

            if (admin.getGender() != null) {

                existingAdmin.setGender(
                        admin.getGender());
            }

            if (admin.getAddress() != null) {

                existingAdmin.setAddress(
                        admin.getAddress());
            }

            if (admin.getProfileImage() != null) {

                existingAdmin.setProfileImage(
                        admin.getProfileImage());
            }

            if (admin.getPassword() != null
                    && !admin.getPassword().isBlank()) {

                existingAdmin.setPassword(
                        passwordEncoder.encode(
                                admin.getPassword()));
            }

            return adminRepo.save(
                    existingAdmin);
        }

        return null;
    }

    public void addDoctor(DoctorModel doctor) {

        doctor.setPassword(
                passwordEncoder.encode(
                        doctor.getPassword()));

        doctorRepo.save(doctor);
    }

    public List<DoctorModel> getAllDoctors() {

        return doctorRepo.findAll();

    }

    public DoctorModel getDoctorById(Long doctorId) {

        Optional<DoctorModel> doctor
                = doctorRepo.findById(doctorId);

        return doctor.orElse(null);
    }

    public DoctorModel updateDoctor(
            Long doctorId,
            DoctorModel doctor) {

        Optional<DoctorModel> optionalDoctor
                = doctorRepo.findById(doctorId);

        if (optionalDoctor.isPresent()) {

            DoctorModel existingDoctor
                    = optionalDoctor.get();

            existingDoctor.setDoctorName(
                    doctor.getDoctorName());

            existingDoctor.setEmail(
                    doctor.getEmail());

            if (doctor.getPassword() != null
                    && !doctor.getPassword().isBlank()) {

                existingDoctor.setPassword(
                        passwordEncoder.encode(
                                doctor.getPassword()));
            }

            existingDoctor.setSpecialization(
                    doctor.getSpecialization());

            existingDoctor.setExperience(
                    doctor.getExperience());

            existingDoctor.setFee(
                    doctor.getFee());

            existingDoctor.setDescription(
                    doctor.getDescription());

            return doctorRepo.save(
                    existingDoctor);
        }

        return null;
    }

    public void deleteDoctor(Long doctorId) {

        doctorRepo.deleteById(
                doctorId);

    }

    public void addPatient(PatientModel patient) {

        patient.setPassword(
                passwordEncoder.encode(
                        patient.getPassword()));

        patientRepo.save(patient);
    }

    public List<PatientModel> getAllPatients() {

        return patientRepo.findAll();

    }

    public PatientModel getPatientById(Long patientId) {

        Optional<PatientModel> patient
                = patientRepo.findById(patientId);

        return patient.orElse(null);
    }

    public PatientModel updatePatient(
            Long patientId,
            PatientModel patient) {

        Optional<PatientModel> optionalPatient
                = patientRepo.findById(patientId);

        if (optionalPatient.isPresent()) {

            PatientModel existingPatient
                    = optionalPatient.get();

            if (patient.getFullName() != null) {

                existingPatient.setFullName(
                        patient.getFullName());
            }

            if (patient.getEmail() != null) {

                existingPatient.setEmail(
                        patient.getEmail());
            }

            if (patient.getMobileNumber() != null) {

                existingPatient.setMobileNumber(
                        patient.getMobileNumber());
            }

            if (patient.getPassword() != null
                    && !patient.getPassword().isBlank()) {

                existingPatient.setPassword(
                        passwordEncoder.encode(
                                patient.getPassword()));
            }

            if (patient.getGender() != null) {

                existingPatient.setGender(
                        patient.getGender());
            }

            if (patient.getDob() != null) {

                existingPatient.setDob(
                        patient.getDob());
            }

            if (patient.getBloodGroup() != null) {

                existingPatient.setBloodGroup(
                        patient.getBloodGroup());
            }

            if (patient.getAddress() != null) {

                existingPatient.setAddress(
                        patient.getAddress());
            }

            return patientRepo.save(
                    existingPatient);
        }

        return null;
    }

    public void deletePatient(Long patientId) {

        patientRepo.deleteById(
                patientId);

    }

    public List<AppointmentModel> getAllAppointments() {

        return appointmentRepo.findAll();

    }

    public AppointmentModel getAppointmentById(
            Long appointmentId) {

        Optional<AppointmentModel> appointment
                = appointmentRepo.findById(
                        appointmentId);

        return appointment.orElse(null);
    }

    public void deleteAppointment(
            Long appointmentId) {

        appointmentRepo.deleteById(
                appointmentId);

    }

    public void deleteAdmin(Long adminId) {

        adminRepo.deleteById(adminId);

    }

    public AdminModel uploadProfileImage(
            Long adminId,
            MultipartFile file) throws IOException {

        AdminModel admin
                = adminRepo.findById(adminId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Admin not found"
                                )
                        );

        if (file == null || file.isEmpty()) {
            throw new RuntimeException(
                    "Image file is required"
            );
        }

        String originalFileName
                = file.getOriginalFilename();

        if (originalFileName == null
                || originalFileName.isBlank()) {

            throw new RuntimeException(
                    "Invalid image file"
            );
        }

        String extension = "";

        int dotIndex
                = originalFileName.lastIndexOf(".");

        if (dotIndex >= 0) {
            extension
                    = originalFileName.substring(
                            dotIndex
                    ).toLowerCase();
        }

        if (!extension.equals(".jpg")
                && !extension.equals(".jpeg")
                && !extension.equals(".png")
                && !extension.equals(".webp")) {

            throw new RuntimeException(
                    "Only JPG, JPEG, PNG and WEBP images are allowed"
            );
        }

        String fileName
                = "admin-"
                + adminId
                + extension;

        Path uploadDirectory
                = Paths.get(
                        "uploads",
                        "profile-images"
                );

        Files.createDirectories(
                uploadDirectory
        );

        Path filePath
                = uploadDirectory.resolve(
                        fileName
                );

        Files.write(
                filePath,
                file.getBytes()
        );

        admin.setProfileImage(
                fileName
        );

        return adminRepo.save(
                admin
        );
    }

}
