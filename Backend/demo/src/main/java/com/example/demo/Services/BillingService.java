package com.example.demo.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.BillingModel;
import com.example.demo.Repository.BillingRepo;

@Service
public class BillingService {

    @Autowired
    private BillingRepo billingRepo;

    public List<BillingModel> getAllBills() {
        return billingRepo.findAll();
    }

    public BillingModel getBillById(Long billId) {

        return billingRepo.findById(billId)
                .orElse(null);
    }

    public BillingModel createBill(
            BillingModel bill) {

        if (bill.getBillDate() == null) {
            bill.setBillDate(
                    LocalDate.now());
        }

        if (bill.getPaidAmount() == null) {
            bill.setPaidAmount(0.0);
        }

        if (bill.getPaymentStatus() == null
                || bill.getPaymentStatus().isBlank()) {

            if (bill.getPaidAmount() <= 0) {
                bill.setPaymentStatus("Pending");

            } else if (bill.getTotalAmount() != null
                    && bill.getPaidAmount()
                    < bill.getTotalAmount()) {

                bill.setPaymentStatus("Partial");

            } else {
                bill.setPaymentStatus("Paid");
            }
        }

        if (bill.getPaymentMethod() == null
                || bill.getPaymentMethod().isBlank()) {

            bill.setPaymentMethod("CASH");
        }

        return billingRepo.save(bill);
    }

    public BillingModel updateBill(
            Long billId,
            BillingModel bill) {

        BillingModel existingBill
                = billingRepo.findById(billId)
                        .orElse(null);

        if (existingBill == null) {
            return null;
        }

        if (bill.getBillNumber() != null) {
            existingBill.setBillNumber(
                    bill.getBillNumber());
        }

        if (bill.getPatientId() != null) {
            existingBill.setPatientId(
                    bill.getPatientId());
        }

        if (bill.getDoctorId() != null) {
            existingBill.setDoctorId(
                    bill.getDoctorId());
        }

        if (bill.getBillDate() != null) {
            existingBill.setBillDate(
                    bill.getBillDate());
        }

        if (bill.getTotalAmount() != null) {
            existingBill.setTotalAmount(
                    bill.getTotalAmount());
        }

        if (bill.getPaidAmount() != null) {
            existingBill.setPaidAmount(
                    bill.getPaidAmount());
        }

        if (bill.getPaymentStatus() != null) {
            existingBill.setPaymentStatus(
                    bill.getPaymentStatus());
        }

        if (bill.getPaymentMethod() != null
                && !bill.getPaymentMethod().isBlank()) {

            existingBill.setPaymentMethod(
                    bill.getPaymentMethod());
        }

        if (bill.getRemarks() != null) {
            existingBill.setRemarks(
                    bill.getRemarks());
        }

        return billingRepo.save(existingBill);
    }

    public BillingModel payBill(
            Long billId) {

        BillingModel bill
                = billingRepo.findById(billId)
                        .orElse(null);

        if (bill == null) {
            return null;
        }

        if (bill.getTotalAmount() == null) {
            return bill;
        }

        bill.setPaidAmount(
                bill.getTotalAmount());

        bill.setPaymentStatus(
                "Paid");

        return billingRepo.save(bill);
    }

    public void deleteBill(Long billId) {

        if (billingRepo.existsById(billId)) {
            billingRepo.deleteById(
                    billId);
        }
    }

    public List<BillingModel> getBillsByPatient(
            Long patientId) {

        return billingRepo.findByPatientId(
                patientId);
    }

    public List<BillingModel> getBillsByDoctor(
            Long doctorId) {

        return billingRepo.findByDoctorId(
                doctorId);
    }

    public List<BillingModel> getBillsByStatus(
            String paymentStatus) {

        return billingRepo.findByPaymentStatus(
                paymentStatus);
    }

    public double getDueAmount(
            BillingModel bill) {

        double total
                = bill.getTotalAmount() != null
                ? bill.getTotalAmount()
                : 0.0;

        double paid
                = bill.getPaidAmount() != null
                ? bill.getPaidAmount()
                : 0.0;

        return Math.max(
                0.0,
                total - paid);
    }
}
