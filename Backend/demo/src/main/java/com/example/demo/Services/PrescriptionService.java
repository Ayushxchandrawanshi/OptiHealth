package com.example.demo.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Models.MedicalHistoryModel;
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

    @Autowired
    private MedicalHistoryService medicalHistoryService;

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
                        savedPrescription.getId());

                prescriptionMedicineRepo.save(medicine);
            }
        }

        MedicalHistoryModel history
                = new MedicalHistoryModel();

        history.setPatientId(
                savedPrescription.getPatientId());

        history.setDoctorId(
                savedPrescription.getDoctorId());

        history.setAppointmentId(
                savedPrescription.getAppointmentId());

        history.setVisitDate(
                savedPrescription.getPrescriptionDate());

        history.setDiagnosis(
                savedPrescription.getDiagnosis());

        history.setPrescription(
                "Prescription ID: "
                + savedPrescription.getId());

        history.setReport(
                savedPrescription.getLabTests());

        history.setRemarks(
                savedPrescription.getSpecialInstructions());

        medicalHistoryService.saveMedicalHistory(history);

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
                appointmentId);
    }

    public List<PrescriptionMedicineModel> getMedicines(
            Long prescriptionId) {

        return prescriptionMedicineRepo
                .findByPrescriptionId(prescriptionId);
    }

    @Transactional
    public void deletePrescription(Long prescriptionId) {

        prescriptionMedicineRepo.deleteByPrescriptionId(
                prescriptionId);

        prescriptionRepo.deleteById(
                prescriptionId);
    }
}
