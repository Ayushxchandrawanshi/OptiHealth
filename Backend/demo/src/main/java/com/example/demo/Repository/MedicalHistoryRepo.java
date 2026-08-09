package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.MedicalHistoryModel;

@Repository
public interface MedicalHistoryRepo extends JpaRepository<MedicalHistoryModel, Long> {

    List<MedicalHistoryModel> findByPatientId(Long patientId);
}
