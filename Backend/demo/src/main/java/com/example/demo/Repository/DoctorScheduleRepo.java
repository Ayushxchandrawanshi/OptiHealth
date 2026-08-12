package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.DoctorScheduleModel;

@Repository
public interface DoctorScheduleRepo
        extends JpaRepository<DoctorScheduleModel, Long> {

    List<DoctorScheduleModel> findByDoctorId(Long doctorId);

    void deleteByDoctorId(Long doctorId);
}
