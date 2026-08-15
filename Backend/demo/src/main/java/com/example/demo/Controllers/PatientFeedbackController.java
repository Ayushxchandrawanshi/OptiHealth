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

import com.example.demo.Models.PatientFeedbackModel;
import com.example.demo.Services.PatientFeedbackService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/patient-feedback")
public class PatientFeedbackController {

    @Autowired
    private PatientFeedbackService patientFeedbackService;

    @GetMapping
    public List<PatientFeedbackModel> getAllFeedback() {

        return patientFeedbackService.getAllFeedback();
    }

    @GetMapping("/{feedbackId}")
    public PatientFeedbackModel getFeedbackById(
            @PathVariable Long feedbackId) {

        return patientFeedbackService.getFeedbackById(
                feedbackId);
    }

    @GetMapping("/patient/{patientId}")
    public List<PatientFeedbackModel> getFeedbackByPatient(
            @PathVariable Long patientId) {

        return patientFeedbackService.getFeedbackByPatient(
                patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<PatientFeedbackModel> getFeedbackByDoctor(
            @PathVariable Long doctorId) {

        return patientFeedbackService.getFeedbackByDoctor(
                doctorId);
    }

    @GetMapping("/appointment/{appointmentId}")
    public List<PatientFeedbackModel> getFeedbackByAppointment(
            @PathVariable Long appointmentId) {

        return patientFeedbackService
                .getFeedbackByAppointment(
                        appointmentId);
    }

    @GetMapping("/average-rating")
    public double getAverageRating() {

        return patientFeedbackService
                .getAverageRating();
    }

    @GetMapping("/average-rating/doctor/{doctorId}")
    public double getAverageDoctorRating(
            @PathVariable Long doctorId) {

        return patientFeedbackService
                .getAverageDoctorRating(
                        doctorId);
    }

    @PostMapping
    public PatientFeedbackModel createFeedback(
            @RequestBody PatientFeedbackModel feedback) {

        return patientFeedbackService.createFeedback(
                feedback);
    }

    @PutMapping("/{feedbackId}")
    public PatientFeedbackModel updateFeedback(
            @PathVariable Long feedbackId,
            @RequestBody PatientFeedbackModel feedback) {

        return patientFeedbackService.updateFeedback(
                feedbackId,
                feedback);
    }

    @DeleteMapping("/{feedbackId}")
    public String deleteFeedback(
            @PathVariable Long feedbackId) {

        patientFeedbackService.deleteFeedback(
                feedbackId);

        return "Feedback Deleted Successfully";
    }
}
