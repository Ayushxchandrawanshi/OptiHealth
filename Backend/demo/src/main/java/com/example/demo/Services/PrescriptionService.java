package com.example.demo.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Models.PrescriptionMedicineModel;
import com.example.demo.Models.PrescriptionModel;
import com.example.demo.Repository.PrescriptionMedicineRepo;
import com.example.demo.Repository.PrescriptionRepo;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepo prescriptionRepo;

    @Autowired
    private PrescriptionMedicineRepo prescriptionMedicineRepo;

    @Transactional
    public PrescriptionModel createPrescription(
            PrescriptionModel prescription,
            List<PrescriptionMedicineModel> medicines) {

        if (prescription.getPrescriptionDate() == null) {
            prescription.setPrescriptionDate(LocalDate.now());
        }

        PrescriptionModel savedPrescription
                = prescriptionRepo.save(prescription);

        if (medicines != null && !medicines.isEmpty()) {

            for (PrescriptionMedicineModel medicine : medicines) {

                medicine.setPrescriptionId(
                        savedPrescription.getId()
                );

                prescriptionMedicineRepo.save(medicine);
            }
        }

        return savedPrescription;
    }

    public PrescriptionModel getPrescriptionById(
            Long prescriptionId) {

        return prescriptionRepo.findById(prescriptionId)
                .orElse(null);
    }

    public List<PrescriptionModel> getPatientPrescriptions(
            Long patientId) {

        return prescriptionRepo.findByPatientId(patientId);
    }

    public List<PrescriptionModel> getDoctorPrescriptions(
            Long doctorId) {

        return prescriptionRepo.findByDoctorId(doctorId);
    }

    public List<PrescriptionModel> getAppointmentPrescriptions(
            Long appointmentId) {

        return prescriptionRepo.findByAppointmentId(
                appointmentId
        );
    }

    public List<PrescriptionMedicineModel> getMedicines(
            Long prescriptionId) {

        return prescriptionMedicineRepo
                .findByPrescriptionId(prescriptionId);
    }

    @Transactional
    public void deletePrescription(Long prescriptionId) {

        prescriptionMedicineRepo.deleteByPrescriptionId(
                prescriptionId
        );

        prescriptionRepo.deleteById(
                prescriptionId
        );
    }
}
