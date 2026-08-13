package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.PatientDashboardDTO;
import com.example.demo.Models.AppointmentModel;
import com.example.demo.Models.DoctorModel;
import com.example.demo.Models.PatientModel;
import com.example.demo.Repository.AppointmentRepo;
import com.example.demo.Repository.DoctorRepo;
import com.example.demo.Repository.PatientRepo;

@Service
public class PatientService {

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerPatient(PatientModel patient) {
        patient.setPassword(
                passwordEncoder.encode(
                        patient.getPassword()
                )
        );

        patientRepo.save(patient);
    }

    public List<DoctorModel> getAllDoctors() {
        return doctorRepo.findAll();
    }

    public void bookAppointment(
            AppointmentModel appointment) {

        appointment.setStatus("PENDING");
        appointmentRepo.save(appointment);
    }

    public List<AppointmentModel> getMyAppointments(
            Long patientId) {

        return appointmentRepo.findByPatientId(
                patientId
        );
    }

    public List<PatientModel> getAllPatients() {
        return patientRepo.findAll();
    }

    public PatientModel getPatientById(
            Long patientId) {

        return patientRepo.findById(patientId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Patient not found"
                        )
                );
    }

    public PatientModel updatePatient(
            Long patientId,
            PatientModel updatedPatient) {

        PatientModel patient
                = patientRepo.findById(patientId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Patient not found"
                                )
                        );

        if (updatedPatient.getFullName() != null) {
            patient.setFullName(
                    updatedPatient.getFullName()
            );
        }

        if (updatedPatient.getEmail() != null) {
            patient.setEmail(
                    updatedPatient.getEmail()
            );
        }

        if (updatedPatient.getMobileNumber() != null) {
            patient.setMobileNumber(
                    updatedPatient.getMobileNumber()
            );
        }

        if (updatedPatient.getGender() != null) {
            patient.setGender(
                    updatedPatient.getGender()
            );
        }

        if (updatedPatient.getDob() != null) {
            patient.setDob(
                    updatedPatient.getDob()
            );
        }

        if (updatedPatient.getBloodGroup() != null) {
            patient.setBloodGroup(
                    updatedPatient.getBloodGroup()
            );
        }

        if (updatedPatient.getAddress() != null) {
            patient.setAddress(
                    updatedPatient.getAddress()
            );
        }

        if (updatedPatient.getCity() != null) {
            patient.setCity(
                    updatedPatient.getCity()
            );
        }

        if (updatedPatient.getState() != null) {
            patient.setState(
                    updatedPatient.getState()
            );
        }

        if (updatedPatient.getPincode() != null) {
            patient.setPincode(
                    updatedPatient.getPincode()
            );
        }

        if (updatedPatient.getEmergencyContact() != null) {
            patient.setEmergencyContact(
                    updatedPatient.getEmergencyContact()
            );
        }

        if (updatedPatient.getHeight() != null) {
            patient.setHeight(
                    updatedPatient.getHeight()
            );
        }

        if (updatedPatient.getWeight() != null) {
            patient.setWeight(
                    updatedPatient.getWeight()
            );
        }

        if (updatedPatient.getAllergies() != null) {
            patient.setAllergies(
                    updatedPatient.getAllergies()
            );
        }

        if (updatedPatient.getExistingDiseases() != null) {
            patient.setExistingDiseases(
                    updatedPatient.getExistingDiseases()
            );
        }

        if (updatedPatient.getCurrentMedications() != null) {
            patient.setCurrentMedications(
                    updatedPatient.getCurrentMedications()
            );
        }

        return patientRepo.save(patient);
    }

    public void deletePatient(
            Long patientId) {

        if (!patientRepo.existsById(patientId)) {
            throw new RuntimeException(
                    "Patient not found"
            );
        }

        patientRepo.deleteById(
                patientId
        );
    }

    public PatientDashboardDTO getDashboardData(
            Long patientId) {

        Optional<PatientModel> optionalPatient
                = patientRepo.findById(patientId);

        if (optionalPatient.isEmpty()) {
            return null;
        }

        PatientModel patient
                = optionalPatient.get();

        List<AppointmentModel> appointments
                = appointmentRepo.findByPatientId(
                        patientId
                );

        long totalDoctors
                = doctorRepo.count();

        String nextDoctor
                = "Not Assigned";

        if (!appointments.isEmpty()) {

            AppointmentModel appointment
                    = appointments.get(0);

            DoctorModel doctor
                    = doctorRepo.findById(
                            appointment.getDoctorId()
                    ).orElse(null);

            if (doctor != null) {
                nextDoctor
                        = doctor.getDoctorName();
            }
        }

        PatientDashboardDTO dashboard
                = new PatientDashboardDTO();

        dashboard.setFullName(
                patient.getFullName()
        );

        dashboard.setEmail(
                patient.getEmail()
        );

        dashboard.setMobileNumber(
                patient.getMobileNumber()
        );

        dashboard.setAppointmentCount(
                appointments.size()
        );

        dashboard.setDoctorCount(
                (int) totalDoctors
        );

        dashboard.setReportCount(0);

        dashboard.setPrescriptionCount(0);

        dashboard.setNextDoctorName(
                nextDoctor
        );

        return dashboard;
    }
}
