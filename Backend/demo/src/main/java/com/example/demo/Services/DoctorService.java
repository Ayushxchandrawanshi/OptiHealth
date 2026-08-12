package com.example.demo.Services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.DoctorAppointmentDTO;
import com.example.demo.Models.AppointmentModel;
import com.example.demo.Models.DoctorModel;
import com.example.demo.Models.PatientModel;
import com.example.demo.Repository.AppointmentRepo;
import com.example.demo.Repository.DoctorRepo;
import com.example.demo.Repository.PatientRepo;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PatientRepo patientRepo;

    public void registerDoctor(DoctorModel doctor) {
        doctor.setPassword(
                passwordEncoder.encode(doctor.getPassword()));
        doctorRepo.save(doctor);
    }

    public List<DoctorAppointmentDTO> getDoctorAppointments(Long doctorId) {

        List<AppointmentModel> appointments
                = appointmentRepo.findByDoctorId(doctorId);

        List<DoctorAppointmentDTO> result
                = new ArrayList<>();

        for (AppointmentModel appointment : appointments) {

            PatientModel patient
                    = patientRepo.findById(
                            appointment.getPatientId())
                            .orElse(null);

            DoctorAppointmentDTO dto
                    = new DoctorAppointmentDTO();

            dto.setAppointmentId(
                    appointment.getAppointmentId());

            dto.setPatientId(
                    appointment.getPatientId());

            dto.setAppointmentDate(
                    appointment.getAppointmentDate());

            dto.setAppointmentTime(
                    appointment.getAppointmentTime());

            dto.setStatus(
                    appointment.getStatus());

            dto.setReason(
                    appointment.getReason());

            dto.setRemarks(
                    appointment.getRemarks());

            if (patient != null) {

                dto.setPatientName(
                        patient.getFullName());

                dto.setPatientEmail(
                        patient.getEmail());

                dto.setMobileNumber(
                        patient.getMobileNumber());

            } else {

                dto.setPatientName(
                        "Patient Not Found");

                dto.setPatientEmail("-");

                dto.setMobileNumber(null);
            }

            result.add(dto);
        }

        return result;
    }

    public void acceptAppointment(
            Long appointmentId,
            LocalDate appointmentDate,
            LocalTime appointmentTime) {

        Optional<AppointmentModel> optionalAppointment
                = appointmentRepo.findById(appointmentId);

        if (optionalAppointment.isPresent()) {

            AppointmentModel appointment
                    = optionalAppointment.get();

            appointment.setAppointmentDate(
                    appointmentDate);

            appointment.setAppointmentTime(
                    appointmentTime);

            appointment.setStatus("ACCEPTED");

            appointmentRepo.save(appointment);
        }
    }

    public void rejectAppointment(
            Long appointmentId) {

        Optional<AppointmentModel> optionalAppointment
                = appointmentRepo.findById(appointmentId);

        if (optionalAppointment.isPresent()) {

            AppointmentModel appointment
                    = optionalAppointment.get();

            appointment.setStatus("REJECTED");

            appointmentRepo.save(appointment);
        }
    }

    public void completeAppointment(
            Long appointmentId) {

        Optional<AppointmentModel> optionalAppointment
                = appointmentRepo.findById(appointmentId);

        if (optionalAppointment.isPresent()) {

            AppointmentModel appointment
                    = optionalAppointment.get();

            appointment.setStatus("COMPLETED");

            appointmentRepo.save(appointment);
        }
    }

    public DoctorModel getDoctorById(
            Long doctorId) {

        return doctorRepo.findById(doctorId)
                .orElse(null);
    }

    public DoctorModel updateDoctorProfile(
            Long doctorId,
            DoctorModel doctor) {

        Optional<DoctorModel> optionalDoctor
                = doctorRepo.findById(doctorId);

        if (optionalDoctor.isEmpty()) {
            return null;
        }

        DoctorModel existingDoctor
                = optionalDoctor.get();

        if (doctor.getDoctorName() != null) {
            existingDoctor.setDoctorName(
                    doctor.getDoctorName());
        }

        if (doctor.getEmail() != null) {
            existingDoctor.setEmail(
                    doctor.getEmail());
        }

        if (doctor.getSpecialization() != null) {
            existingDoctor.setSpecialization(
                    doctor.getSpecialization());
        }

        existingDoctor.setExperience(
                doctor.getExperience());

        existingDoctor.setFee(
                doctor.getFee());

        if (doctor.getDescription() != null) {
            existingDoctor.setDescription(
                    doctor.getDescription());
        }

        if (doctor.getMobileNumber() != null) {
            existingDoctor.setMobileNumber(
                    doctor.getMobileNumber());
        }

        if (doctor.getDob() != null) {
            existingDoctor.setDob(
                    doctor.getDob());
        }

        if (doctor.getGender() != null) {
            existingDoctor.setGender(
                    doctor.getGender());
        }

        if (doctor.getBloodGroup() != null) {
            existingDoctor.setBloodGroup(
                    doctor.getBloodGroup());
        }

        if (doctor.getQualification() != null) {
            existingDoctor.setQualification(
                    doctor.getQualification());
        }

        if (doctor.getRegistrationNo() != null) {
            existingDoctor.setRegistrationNo(
                    doctor.getRegistrationNo());
        }

        if (doctor.getClinicAddress() != null) {
            existingDoctor.setClinicAddress(
                    doctor.getClinicAddress());
        }

        if (doctor.getLanguages() != null) {
            existingDoctor.setLanguages(
                    doctor.getLanguages());
        }

        if (doctor.getLinkedin() != null) {
            existingDoctor.setLinkedin(
                    doctor.getLinkedin());
        }

        if (doctor.getWebsite() != null) {
            existingDoctor.setWebsite(
                    doctor.getWebsite());
        }

        if (doctor.getFacebook() != null) {
            existingDoctor.setFacebook(
                    doctor.getFacebook());
        }

        if (doctor.getInstagram() != null) {
            existingDoctor.setInstagram(
                    doctor.getInstagram());
        }

        return doctorRepo.save(existingDoctor);
    }
}
