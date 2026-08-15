package com.example.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.BillingModel;
import com.example.demo.Services.BillingService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @GetMapping
    public List<BillingModel> getAllBills() {

        return billingService.getAllBills();
    }

    @GetMapping("/{billId}")
    public BillingModel getBillById(
            @PathVariable Long billId) {

        return billingService.getBillById(
                billId);
    }

    @GetMapping("/patient/{patientId}")
    public List<BillingModel> getBillsByPatient(
            @PathVariable Long patientId) {

        return billingService.getBillsByPatient(
                patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<BillingModel> getBillsByDoctor(
            @PathVariable Long doctorId) {

        return billingService.getBillsByDoctor(
                doctorId);
    }

    @GetMapping("/status/{paymentStatus}")
    public List<BillingModel> getBillsByStatus(
            @PathVariable String paymentStatus) {

        return billingService.getBillsByStatus(
                paymentStatus);
    }

    @PostMapping
    public BillingModel createBill(
            @RequestBody BillingModel bill) {

        return billingService.createBill(
                bill);
    }

    @PutMapping("/{billId}")
    public BillingModel updateBill(
            @PathVariable Long billId,
            @RequestBody BillingModel bill) {

        return billingService.updateBill(
                billId,
                bill);
    }

    @PutMapping("/pay/{billId}")
    public BillingModel payBill(
            @PathVariable Long billId) {

        return billingService.payBill(
                billId);
    }

    @DeleteMapping("/{billId}")
    public String deleteBill(
            @PathVariable Long billId) {

        billingService.deleteBill(
                billId);

        return "Bill Deleted Successfully";
    }
}
