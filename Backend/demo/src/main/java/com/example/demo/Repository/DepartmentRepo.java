package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.DepartmentModel;

@Repository
public interface DepartmentRepo
        extends JpaRepository<DepartmentModel, Long> {

    List<DepartmentModel> findByStatus(String status);

    DepartmentModel findByDepartmentCode(
            String departmentCode);

    DepartmentModel findByDepartmentName(
            String departmentName);
}
