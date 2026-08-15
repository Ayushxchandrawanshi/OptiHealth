package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.BillingModel;

@Repository
public interface BillingRepo
        extends JpaRepository<BillingModel, Long> {

    List<BillingModel> findByPatientId(
            Long patientId);

    List<BillingModel> findByDoctorId(
            Long doctorId);

    List<BillingModel> findByPaymentStatus(
            String paymentStatus);
}
