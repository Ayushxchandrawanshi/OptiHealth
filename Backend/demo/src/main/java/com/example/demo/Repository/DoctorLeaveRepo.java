package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.DoctorLeaveModel;

@Repository
public interface DoctorLeaveRepo
        extends JpaRepository<DoctorLeaveModel, Long> {

    List<DoctorLeaveModel> findByDoctorId(Long doctorId);

    void deleteByDoctorId(Long doctorId);
}
