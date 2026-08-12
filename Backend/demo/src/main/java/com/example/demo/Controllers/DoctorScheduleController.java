package com.example.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.DoctorLeaveModel;
import com.example.demo.Models.DoctorScheduleModel;
import com.example.demo.Services.DoctorScheduleService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/doctors")
public class DoctorScheduleController {

    @Autowired
    private DoctorScheduleService doctorScheduleService;

    @GetMapping("/{doctorId}/schedule")
    public List<DoctorScheduleModel> getDoctorSchedule(
            @PathVariable Long doctorId) {

        return doctorScheduleService
                .getDoctorSchedule(doctorId);
    }

    @PostMapping("/schedule")
    public List<DoctorScheduleModel> saveDoctorSchedule(
            @RequestBody ScheduleRequest request) {

        return doctorScheduleService.saveDoctorSchedule(
                request.getDoctorId(),
                request.getSchedules()
        );
    }

    @GetMapping("/{doctorId}/leaves")
    public List<DoctorLeaveModel> getDoctorLeaves(
            @PathVariable Long doctorId) {

        return doctorScheduleService
                .getDoctorLeaves(doctorId);
    }

    @PostMapping("/leave")
    public DoctorLeaveModel saveDoctorLeave(
            @RequestBody LeaveRequest request) {

        return doctorScheduleService.saveDoctorLeave(
                request.getDoctorId(),
                request.getLeave()
        );
    }

    public static class ScheduleRequest {

        private Long doctorId;
        private List<DoctorScheduleModel> schedules;

        public ScheduleRequest() {
        }

        public Long getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(Long doctorId) {
            this.doctorId = doctorId;
        }

        public List<DoctorScheduleModel> getSchedules() {
            return schedules;
        }

        public void setSchedules(
                List<DoctorScheduleModel> schedules) {

            this.schedules = schedules;
        }
    }

    public static class LeaveRequest {

        private Long doctorId;
        private DoctorLeaveModel leave;

        public LeaveRequest() {
        }

        public Long getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(Long doctorId) {
            this.doctorId = doctorId;
        }

        public DoctorLeaveModel getLeave() {
            return leave;
        }

        public void setLeave(
                DoctorLeaveModel leave) {

            this.leave = leave;
        }
    }
}
