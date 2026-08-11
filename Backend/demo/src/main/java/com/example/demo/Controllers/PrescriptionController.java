package com.example.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.PrescriptionMedicineModel;
import com.example.demo.Models.PrescriptionModel;
import com.example.demo.Services.PrescriptionService;

@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin(origins = {
    "http://localhost:5500",
    "http://127.0.0.1:5500"
})
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<PrescriptionModel> createPrescription(
            @RequestBody PrescriptionRequest request) {

        PrescriptionModel savedPrescription
                = prescriptionService.createPrescription(
                        request.getPrescription(),
                        request.getMedicines()
                );

        return ResponseEntity.ok(savedPrescription);
    }

    @GetMapping("/{prescriptionId}")
    public ResponseEntity<PrescriptionModel> getPrescriptionById(
            @PathVariable Long prescriptionId) {

        PrescriptionModel prescription
                = prescriptionService.getPrescriptionById(
                        prescriptionId
                );

        if (prescription == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(prescription);
    }

    @GetMapping("/{prescriptionId}/medicines")
    public ResponseEntity<List<PrescriptionMedicineModel>> getMedicines(
            @PathVariable Long prescriptionId) {

        return ResponseEntity.ok(
                prescriptionService.getMedicines(
                        prescriptionId
                )
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PrescriptionModel>> getPatientPrescriptions(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                prescriptionService.getPatientPrescriptions(
                        patientId
                )
        );
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<PrescriptionModel>> getDoctorPrescriptions(
            @PathVariable Long doctorId) {

        return ResponseEntity.ok(
                prescriptionService.getDoctorPrescriptions(
                        doctorId
                )
        );
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<PrescriptionModel>> getAppointmentPrescriptions(
            @PathVariable Long appointmentId) {

        return ResponseEntity.ok(
                prescriptionService.getAppointmentPrescriptions(
                        appointmentId
                )
        );
    }

    @DeleteMapping("/{prescriptionId}")
    public ResponseEntity<Void> deletePrescription(
            @PathVariable Long prescriptionId) {

        PrescriptionModel prescription
                = prescriptionService.getPrescriptionById(
                        prescriptionId
                );

        if (prescription == null) {
            return ResponseEntity.notFound().build();
        }

        prescriptionService.deletePrescription(
                prescriptionId
        );

        return ResponseEntity.noContent().build();
    }

    public static class PrescriptionRequest {

        private PrescriptionModel prescription;

        private List<PrescriptionMedicineModel> medicines;

        public PrescriptionRequest() {
        }

        public PrescriptionModel getPrescription() {
            return prescription;
        }

        public void setPrescription(
                PrescriptionModel prescription) {

            this.prescription = prescription;
        }

        public List<PrescriptionMedicineModel> getMedicines() {
            return medicines;
        }

        public void setMedicines(
                List<PrescriptionMedicineModel> medicines) {

            this.medicines = medicines;
        }
    }
}
