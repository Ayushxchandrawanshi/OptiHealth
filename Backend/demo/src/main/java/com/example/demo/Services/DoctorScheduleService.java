package com.example.demo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Models.DoctorLeaveModel;
import com.example.demo.Models.DoctorScheduleModel;
import com.example.demo.Repository.DoctorLeaveRepo;
import com.example.demo.Repository.DoctorScheduleRepo;

@Service
public class DoctorScheduleService {

    @Autowired
    private DoctorScheduleRepo doctorScheduleRepo;

    @Autowired
    private DoctorLeaveRepo doctorLeaveRepo;

    public List<DoctorScheduleModel> getDoctorSchedule(Long doctorId) {
        return doctorScheduleRepo.findByDoctorId(doctorId);
    }

    @Transactional
    public List<DoctorScheduleModel> saveDoctorSchedule(
            Long doctorId,
            List<DoctorScheduleModel> schedules) {

        doctorScheduleRepo.deleteByDoctorId(doctorId);

        for (DoctorScheduleModel schedule : schedules) {
            schedule.setId(null);
            schedule.setDoctorId(doctorId);
        }

        return doctorScheduleRepo.saveAll(schedules);
    }

    public List<DoctorLeaveModel> getDoctorLeaves(Long doctorId) {
        return doctorLeaveRepo.findByDoctorId(doctorId);
    }

    public DoctorLeaveModel saveDoctorLeave(
            Long doctorId,
            DoctorLeaveModel leave) {

        leave.setId(null);
        leave.setDoctorId(doctorId);

        return doctorLeaveRepo.save(leave);
    }
}
