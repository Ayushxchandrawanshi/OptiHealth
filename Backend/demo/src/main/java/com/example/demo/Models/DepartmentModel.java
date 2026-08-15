package com.example.demo.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DepartmentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String departmentCode;
    private String departmentName;
    private Long headDoctorId;
    private String status;
    private String description;

    public DepartmentModel() {
    }

    public DepartmentModel(
            String departmentCode,
            String departmentName,
            Long headDoctorId,
            String status,
            String description) {

        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        this.headDoctorId = headDoctorId;
        this.status = status;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getHeadDoctorId() {
        return headDoctorId;
    }

    public void setHeadDoctorId(Long headDoctorId) {
        this.headDoctorId = headDoctorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
