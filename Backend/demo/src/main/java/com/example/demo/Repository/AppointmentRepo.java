package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.AppointmentModel;

@Repository
public interface AppointmentRepo extends JpaRepository<AppointmentModel, Long> {

    List<AppointmentModel> findByDoctorId(Long doctorId);

    List<AppointmentModel> findByPatientId(Long patientId);

    List<AppointmentModel> findByStatus(String status);

}
