package com.example.demo.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DoctorScheduleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long doctorId;
    private String day;
    private Boolean available;

    private String morningStart;
    private String morningEnd;

    private String eveningStart;
    private String eveningEnd;

    private Integer consultationDuration;
    private Integer appointmentLimit;

    private String breakStart;
    private String breakEnd;

    public DoctorScheduleModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getMorningStart() {
        return morningStart;
    }

    public void setMorningStart(String morningStart) {
        this.morningStart = morningStart;
    }

    public String getMorningEnd() {
        return morningEnd;
    }

    public void setMorningEnd(String morningEnd) {
        this.morningEnd = morningEnd;
    }

    public String getEveningStart() {
        return eveningStart;
    }

    public void setEveningStart(String eveningStart) {
        this.eveningStart = eveningStart;
    }

    public String getEveningEnd() {
        return eveningEnd;
    }

    public void setEveningEnd(String eveningEnd) {
        this.eveningEnd = eveningEnd;
    }

    public Integer getConsultationDuration() {
        return consultationDuration;
    }

    public void setConsultationDuration(Integer consultationDuration) {
        this.consultationDuration = consultationDuration;
    }

    public Integer getAppointmentLimit() {
        return appointmentLimit;
    }

    public void setAppointmentLimit(Integer appointmentLimit) {
        this.appointmentLimit = appointmentLimit;
    }

    public String getBreakStart() {
        return breakStart;
    }

    public void setBreakStart(String breakStart) {
        this.breakStart = breakStart;
    }

    public String getBreakEnd() {
        return breakEnd;
    }

    public void setBreakEnd(String breakEnd) {
        this.breakEnd = breakEnd;
    }
}
