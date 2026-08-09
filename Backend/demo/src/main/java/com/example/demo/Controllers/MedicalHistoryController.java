package com.example.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.MedicalHistoryModel;
import com.example.demo.Services.MedicalHistoryService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/medical-history")
public class MedicalHistoryController {

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @GetMapping("/patient/{patientId}")
    public List<MedicalHistoryModel> getPatientHistory(@PathVariable Long patientId) {
        return medicalHistoryService.getPatientHistory(patientId);
    }

    @PostMapping
    public MedicalHistoryModel saveMedicalHistory(@RequestBody MedicalHistoryModel history) {
        return medicalHistoryService.saveMedicalHistory(history);
    }
}
