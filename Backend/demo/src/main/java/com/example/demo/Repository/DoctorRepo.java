package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.DoctorModel;

@Repository
public interface DoctorRepo extends JpaRepository<DoctorModel, Long> {

    DoctorModel findByEmail(String email);

}
