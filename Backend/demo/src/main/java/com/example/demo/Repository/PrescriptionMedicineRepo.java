package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.PrescriptionMedicineModel;

@Repository
public interface PrescriptionMedicineRepo
        extends JpaRepository<PrescriptionMedicineModel, Long> {

    List<PrescriptionMedicineModel> findByPrescriptionId(
            Long prescriptionId
    );

    void deleteByPrescriptionId(Long prescriptionId);
}
