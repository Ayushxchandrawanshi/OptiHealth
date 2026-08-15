package com.example.demo.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.PatientFeedbackModel;
import com.example.demo.Repository.PatientFeedbackRepo;

@Service
public class PatientFeedbackService {

    @Autowired
    private PatientFeedbackRepo patientFeedbackRepo;

    public List<PatientFeedbackModel> getAllFeedback() {
        return patientFeedbackRepo.findAll();
    }

    public PatientFeedbackModel getFeedbackById(
            Long feedbackId) {

        return patientFeedbackRepo.findById(
                feedbackId
        ).orElse(null);
    }

    public PatientFeedbackModel createFeedback(
            PatientFeedbackModel feedback) {

        if (feedback.getRating() == null
                || feedback.getRating() < 1
                || feedback.getRating() > 5) {

            throw new IllegalArgumentException(
                    "Rating must be between 1 and 5"
            );
        }

        if (feedback.getCreatedAt() == null) {
            feedback.setCreatedAt(
                    LocalDateTime.now()
            );
        }

        return patientFeedbackRepo.save(
                feedback
        );
    }

    public PatientFeedbackModel updateFeedback(
            Long feedbackId,
            PatientFeedbackModel feedback) {

        PatientFeedbackModel existingFeedback
                = patientFeedbackRepo.findById(
                        feedbackId
                ).orElse(null);

        if (existingFeedback == null) {
            return null;
        }

        if (feedback.getPatientId() != null) {
            existingFeedback.setPatientId(
                    feedback.getPatientId()
            );
        }

        if (feedback.getDoctorId() != null) {
            existingFeedback.setDoctorId(
                    feedback.getDoctorId()
            );
        }

        if (feedback.getAppointmentId() != null) {
            existingFeedback.setAppointmentId(
                    feedback.getAppointmentId()
            );
        }

        if (feedback.getRating() != null) {

            if (feedback.getRating() < 1
                    || feedback.getRating() > 5) {

                throw new IllegalArgumentException(
                        "Rating must be between 1 and 5"
                );
            }

            existingFeedback.setRating(
                    feedback.getRating()
            );
        }

        if (feedback.getFeedback() != null) {
            existingFeedback.setFeedback(
                    feedback.getFeedback()
            );
        }

        return patientFeedbackRepo.save(
                existingFeedback
        );
    }

    public void deleteFeedback(
            Long feedbackId) {

        if (patientFeedbackRepo.existsById(
                feedbackId)) {

            patientFeedbackRepo.deleteById(
                    feedbackId
            );
        }
    }

    public List<PatientFeedbackModel> getFeedbackByPatient(
            Long patientId) {

        return patientFeedbackRepo.findByPatientId(
                patientId
        );
    }

    public List<PatientFeedbackModel> getFeedbackByDoctor(
            Long doctorId) {

        return patientFeedbackRepo.findByDoctorId(
                doctorId
        );
    }

    public List<PatientFeedbackModel> getFeedbackByAppointment(
            Long appointmentId) {

        return patientFeedbackRepo.findByAppointmentId(
                appointmentId
        );
    }

    public double getAverageRating() {

        List<PatientFeedbackModel> feedbackList
                = patientFeedbackRepo.findAll();

        if (feedbackList.isEmpty()) {
            return 0.0;
        }

        int totalRating = 0;
        int validRatings = 0;

        for (PatientFeedbackModel feedback
                : feedbackList) {

            if (feedback.getRating() != null) {

                totalRating
                        += feedback.getRating();

                validRatings++;
            }
        }

        if (validRatings == 0) {
            return 0.0;
        }

        return (double) totalRating
                / validRatings;
    }

    public double getAverageDoctorRating(
            Long doctorId) {

        List<PatientFeedbackModel> feedbackList
                = patientFeedbackRepo.findByDoctorId(
                        doctorId
                );

        if (feedbackList.isEmpty()) {
            return 0.0;
        }

        int totalRating = 0;
        int validRatings = 0;

        for (PatientFeedbackModel feedback
                : feedbackList) {

            if (feedback.getRating() != null) {

                totalRating
                        += feedback.getRating();

                validRatings++;
            }
        }

        if (validRatings == 0) {
            return 0.0;
        }

        return (double) totalRating
                / validRatings;
    }
}
