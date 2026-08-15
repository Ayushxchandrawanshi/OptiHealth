package com.example.demo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.DepartmentModel;
import com.example.demo.Repository.DepartmentRepo;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    public List<DepartmentModel> getAllDepartments() {
        return departmentRepo.findAll();
    }

    public DepartmentModel getDepartmentById(
            Long departmentId) {

        return departmentRepo.findById(departmentId)
                .orElse(null);
    }

    public DepartmentModel createDepartment(
            DepartmentModel department) {

        return departmentRepo.save(department);
    }

    public DepartmentModel updateDepartment(
            Long departmentId,
            DepartmentModel department) {

        DepartmentModel existingDepartment
                = departmentRepo.findById(departmentId)
                        .orElse(null);

        if (existingDepartment == null) {
            return null;
        }

        if (department.getDepartmentCode() != null) {
            existingDepartment.setDepartmentCode(
                    department.getDepartmentCode());
        }

        if (department.getDepartmentName() != null) {
            existingDepartment.setDepartmentName(
                    department.getDepartmentName());
        }

        if (department.getHeadDoctorId() != null) {
            existingDepartment.setHeadDoctorId(
                    department.getHeadDoctorId());
        }

        if (department.getStatus() != null) {
            existingDepartment.setStatus(
                    department.getStatus());
        }

        if (department.getDescription() != null) {
            existingDepartment.setDescription(
                    department.getDescription());
        }

        return departmentRepo.save(
                existingDepartment);
    }

    public void deleteDepartment(
            Long departmentId) {

        if (departmentRepo.existsById(departmentId)) {
            departmentRepo.deleteById(
                    departmentId);
        }
    }

    public List<DepartmentModel> getDepartmentsByStatus(
            String status) {

        return departmentRepo.findByStatus(
                status);
    }
}
