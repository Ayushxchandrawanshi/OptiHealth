package com.example.demo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.MedicalHistoryModel;
import com.example.demo.Repository.MedicalHistoryRepo;

@Service
public class MedicalHistoryService {

    @Autowired
    private MedicalHistoryRepo medicalHistoryRepo;

    public List<MedicalHistoryModel> getPatientHistory(Long patientId) {
        return medicalHistoryRepo.findByPatientId(patientId);
    }

    public MedicalHistoryModel saveMedicalHistory(MedicalHistoryModel history) {
        return medicalHistoryRepo.save(history);
    }
}
