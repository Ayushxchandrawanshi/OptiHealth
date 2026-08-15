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

import com.example.demo.Models.DepartmentModel;
import com.example.demo.Services.DepartmentService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public List<DepartmentModel> getAllDepartments() {

        return departmentService.getAllDepartments();
    }

    @GetMapping("/{departmentId}")
    public DepartmentModel getDepartmentById(
            @PathVariable Long departmentId) {

        return departmentService.getDepartmentById(
                departmentId);
    }

    @GetMapping("/status/{status}")
    public List<DepartmentModel> getDepartmentsByStatus(
            @PathVariable String status) {

        return departmentService.getDepartmentsByStatus(
                status);
    }

    @PostMapping
    public DepartmentModel createDepartment(
            @RequestBody DepartmentModel department) {

        return departmentService.createDepartment(
                department);
    }

    @PutMapping("/{departmentId}")
    public DepartmentModel updateDepartment(
            @PathVariable Long departmentId,
            @RequestBody DepartmentModel department) {

        return departmentService.updateDepartment(
                departmentId,
                department);
    }

    @DeleteMapping("/{departmentId}")
    public String deleteDepartment(
            @PathVariable Long departmentId) {

        departmentService.deleteDepartment(
                departmentId);

        return "Department Deleted Successfully";
    }
}
