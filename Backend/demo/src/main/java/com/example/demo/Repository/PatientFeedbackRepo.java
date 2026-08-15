package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.PatientFeedbackModel;

@Repository
public interface PatientFeedbackRepo
        extends JpaRepository<PatientFeedbackModel, Long> {

    List<PatientFeedbackModel> findByPatientId(
            Long patientId);

    List<PatientFeedbackModel> findByDoctorId(
            Long doctorId);

    List<PatientFeedbackModel> findByAppointmentId(
            Long appointmentId);

    List<PatientFeedbackModel> findByRating(
            Integer rating);
}
