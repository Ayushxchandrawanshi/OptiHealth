package com.example.demo.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.AppointmentModel;
import com.example.demo.Models.DoctorModel;
import com.example.demo.Models.PatientModel;
import com.example.demo.Repository.AppointmentRepo;
import com.example.demo.Repository.DoctorRepo;
import com.example.demo.Repository.PatientRepo;

@Service
public class AdminDashboardService {

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private AppointmentRepo appointmentRepo;

    public Map<String, Object> getDashboardData() {

        Map<String, Object> dashboard
                = new HashMap<>();

        List<AppointmentModel> appointments
                = appointmentRepo.findAll();

        long totalDoctors
                = doctorRepo.count();

        long totalPatients
                = patientRepo.count();

        long totalAppointments
                = appointmentRepo.count();

        LocalDate today
                = LocalDate.now();

        long todayAppointments
                = appointments.stream()
                        .filter(appointment
                                -> appointment.getAppointmentDate() != null
                        && appointment.getAppointmentDate()
                                .isEqual(today))
                        .count();

        long pendingAppointments
                = appointments.stream()
                        .filter(appointment
                                -> "PENDING".equalsIgnoreCase(
                                appointment.getStatus()))
                        .count();

        long completedAppointments
                = appointments.stream()
                        .filter(appointment
                                -> "COMPLETED".equalsIgnoreCase(
                                appointment.getStatus()))
                        .count();

        long rejectedAppointments
                = appointments.stream()
                        .filter(appointment
                                -> "REJECTED".equalsIgnoreCase(
                                appointment.getStatus()))
                        .count();

        long acceptedAppointments
                = appointments.stream()
                        .filter(appointment
                                -> "ACCEPTED".equalsIgnoreCase(
                                appointment.getStatus()))
                        .count();

        List<Map<String, Object>> recentAppointments
                = new ArrayList<>();

        appointments.stream()
                .sorted((a, b) -> {

                    if (a.getAppointmentDate() == null
                            && b.getAppointmentDate() == null) {
                        return 0;
                    }

                    if (a.getAppointmentDate() == null) {
                        return 1;
                    }

                    if (b.getAppointmentDate() == null) {
                        return -1;
                    }

                    int dateCompare
                            = b.getAppointmentDate()
                                    .compareTo(
                                            a.getAppointmentDate());

                    if (dateCompare != 0) {
                        return dateCompare;
                    }

                    if (a.getAppointmentTime() == null
                            && b.getAppointmentTime() == null) {
                        return 0;
                    }

                    if (a.getAppointmentTime() == null) {
                        return 1;
                    }

                    if (b.getAppointmentTime() == null) {
                        return -1;
                    }

                    return b.getAppointmentTime()
                            .compareTo(
                                    a.getAppointmentTime());
                })
                .limit(5)
                .forEach(appointment -> {

                    Map<String, Object> data
                            = new HashMap<>();

                    data.put(
                            "appointmentId",
                            appointment.getAppointmentId());

                    data.put(
                            "patientId",
                            appointment.getPatientId());

                    data.put(
                            "doctorId",
                            appointment.getDoctorId());

                    data.put(
                            "appointmentDate",
                            appointment.getAppointmentDate());

                    data.put(
                            "appointmentTime",
                            appointment.getAppointmentTime());

                    data.put(
                            "status",
                            appointment.getStatus());

                    data.put(
                            "reason",
                            appointment.getReason());

                    PatientModel patient
                            = patientRepo.findById(
                                    appointment.getPatientId())
                                    .orElse(null);

                    DoctorModel doctor
                            = doctorRepo.findById(
                                    appointment.getDoctorId())
                                    .orElse(null);

                    data.put(
                            "patientName",
                            patient != null
                                    ? patient.getFullName()
                                    : "Patient Not Found");

                    data.put(
                            "doctorName",
                            doctor != null
                                    ? doctor.getDoctorName()
                                    : "Doctor Not Found");

                    data.put(
                            "department",
                            doctor != null
                                    ? doctor.getSpecialization()
                                    : "--");

                    recentAppointments.add(data);
                });

        dashboard.put(
                "totalDoctors",
                totalDoctors);

        dashboard.put(
                "totalPatients",
                totalPatients);

        dashboard.put(
                "totalAppointments",
                totalAppointments);

        dashboard.put(
                "todayAppointments",
                todayAppointments);

        dashboard.put(
                "pendingAppointments",
                pendingAppointments);

        dashboard.put(
                "completedAppointments",
                completedAppointments);

        dashboard.put(
                "rejectedAppointments",
                rejectedAppointments);

        dashboard.put(
                "acceptedAppointments",
                acceptedAppointments);

        dashboard.put(
                "recentAppointments",
                recentAppointments);

        dashboard.put(
                "departments",
                null);

        dashboard.put(
                "todayRevenue",
                null);

        dashboard.put(
                "pendingBills",
                null);

        return dashboard;
    }
}
