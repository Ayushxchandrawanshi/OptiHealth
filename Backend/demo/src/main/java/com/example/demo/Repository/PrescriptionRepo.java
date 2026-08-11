package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.PrescriptionModel;

@Repository
public interface PrescriptionRepo
        extends JpaRepository<PrescriptionModel, Long> {

    List<PrescriptionModel> findByPatientId(Long patientId);

    List<PrescriptionModel> findByDoctorId(Long doctorId);

    List<PrescriptionModel> findByAppointmentId(Long appointmentId);
}
