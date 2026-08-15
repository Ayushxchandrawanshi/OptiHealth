package com.example.demo.Services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.ReportsAnalyticsDTO;
import com.example.demo.Models.AppointmentModel;
import com.example.demo.Models.BillingModel;
import com.example.demo.Models.DepartmentModel;
import com.example.demo.Models.DoctorModel;
import com.example.demo.Models.PatientFeedbackModel;
import com.example.demo.Models.PatientModel;
import com.example.demo.Repository.AppointmentRepo;
import com.example.demo.Repository.BillingRepo;
import com.example.demo.Repository.DepartmentRepo;
import com.example.demo.Repository.DoctorRepo;
import com.example.demo.Repository.PatientFeedbackRepo;
import com.example.demo.Repository.PatientRepo;

@Service
public class ReportsAnalyticsService {

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private BillingRepo billingRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private PatientFeedbackRepo patientFeedbackRepo;

    public ReportsAnalyticsDTO getAnalytics(
            Integer year,
            String departmentFilter) {

        int selectedYear
                = year != null
                        ? year
                        : LocalDate.now().getYear();

        List<PatientModel> patients
                = patientRepo.findAll();

        List<DoctorModel> doctors
                = doctorRepo.findAll();

        List<AppointmentModel> allAppointments
                = appointmentRepo.findAll();

        List<BillingModel> allBills
                = billingRepo.findAll();

        List<DepartmentModel> departments
                = departmentRepo.findAll();

        List<PatientFeedbackModel> allFeedback
                = patientFeedbackRepo.findAll();

        Set<Long> selectedDoctorIds
                = getSelectedDoctorIds(
                        doctors,
                        departmentFilter);

        List<AppointmentModel> appointments
                = filterAppointments(
                        allAppointments,
                        selectedYear,
                        selectedDoctorIds);

        List<BillingModel> bills
                = filterBills(
                        allBills,
                        selectedYear,
                        selectedDoctorIds);

        List<PatientModel> filteredPatients
                = filterPatients(
                        patients,
                        selectedYear,
                        selectedDoctorIds,
                        appointments);

        List<PatientFeedbackModel> feedback
                = filterFeedback(
                        allFeedback,
                        selectedYear,
                        selectedDoctorIds);

        ReportsAnalyticsDTO dto
                = new ReportsAnalyticsDTO();

        dto.setTotalRevenue(
                calculateTotalRevenue(bills));

        dto.setTotalPatients(
                filteredPatients.size());

        dto.setTotalAppointments(
                appointments.size());

        dto.setTotalDoctors(
                getFilteredDoctorCount(
                        doctors,
                        selectedDoctorIds,
                        departmentFilter));

        dto.setAppointmentCompletionRate(
                calculateCompletionRate(
                        appointments));

        dto.setPatientSatisfaction(
                calculateAverageRating(
                        feedback));

        dto.setMonthlyRevenue(
                calculateMonthlyRevenue(
                        bills,
                        selectedYear));

        dto.setAppointmentStatus(
                calculateAppointmentStatus(
                        appointments));

        dto.setDepartmentPerformance(
                calculateDepartmentPerformance(
                        departments,
                        doctors,
                        appointments,
                        bills,
                        feedback,
                        selectedYear,
                        departmentFilter));

        dto.setPatientGrowth(
                calculatePatientGrowth(
                        patients,
                        selectedYear,
                        departmentFilter,
                        selectedDoctorIds,
                        appointments));

        dto.setPaymentMethodRevenue(
                calculatePaymentMethodRevenue(
                        bills));

        dto.setDoctorPerformance(
                calculateDoctorPerformance(
                        doctors,
                        appointments,
                        bills,
                        feedback,
                        departmentFilter));

        dto.setWeeklyAppointmentStatus(
                calculateWeeklyAppointmentStatus(
                        appointments));

        dto.setAnalyticsInsight(
                buildAnalyticsInsight(
                        dto));

        return dto;
    }

    private Set<Long> getSelectedDoctorIds(
            List<DoctorModel> doctors,
            String departmentFilter) {

        Set<Long> doctorIds
                = new LinkedHashSet<>();

        if (departmentFilter == null
                || departmentFilter.isBlank()
                || departmentFilter.equalsIgnoreCase("all")) {

            doctors.forEach(
                    doctor -> doctorIds.add(
                            doctor.getId()));

            return doctorIds;
        }

        doctors.stream()
                .filter(doctor
                        -> doctor.getSpecialization() != null
                && doctor.getSpecialization()
                        .equalsIgnoreCase(
                                departmentFilter))
                .forEach(
                        doctor -> doctorIds.add(
                                doctor.getId()));

        return doctorIds;
    }

    private List<AppointmentModel> filterAppointments(
            List<AppointmentModel> appointments,
            int year,
            Set<Long> doctorIds) {

        return appointments.stream()
                .filter(
                        appointment
                        -> appointment.getAppointmentDate() != null)
                .filter(
                        appointment
                        -> appointment.getAppointmentDate()
                                .getYear() == year)
                .filter(
                        appointment
                        -> doctorIds.isEmpty()
                        || doctorIds.contains(
                                appointment.getDoctorId()))
                .collect(Collectors.toList());
    }

    private List<BillingModel> filterBills(
            List<BillingModel> bills,
            int year,
            Set<Long> doctorIds) {

        return bills.stream()
                .filter(
                        bill
                        -> bill.getBillDate() != null)
                .filter(
                        bill
                        -> bill.getBillDate()
                                .getYear() == year)
                .filter(
                        bill
                        -> doctorIds.isEmpty()
                        || doctorIds.contains(
                                bill.getDoctorId()))
                .collect(Collectors.toList());
    }

    private List<PatientModel> filterPatients(
            List<PatientModel> patients,
            int year,
            Set<Long> doctorIds,
            List<AppointmentModel> appointments) {

        Set<Long> appointmentPatientIds
                = appointments.stream()
                        .map(
                                AppointmentModel::getPatientId)
                        .filter(
                                id -> id != null)
                        .collect(Collectors.toSet());

        return patients.stream()
                .filter(
                        patient -> {

                            if (patient.getRegistrationDate() != null
                            && patient.getRegistrationDate()
                                    .getYear() == year) {

                                return true;
                            }

                            return appointmentPatientIds
                                    .contains(
                                            patient.getId());
                        })
                .collect(Collectors.toList());
    }

    private List<PatientFeedbackModel> filterFeedback(
            List<PatientFeedbackModel> feedbackList,
            int year,
            Set<Long> doctorIds) {

        return feedbackList.stream()
                .filter(
                        feedback
                        -> feedback.getCreatedAt() != null)
                .filter(
                        feedback
                        -> feedback.getCreatedAt()
                                .getYear() == year)
                .filter(
                        feedback
                        -> doctorIds.isEmpty()
                        || doctorIds.contains(
                                feedback.getDoctorId()))
                .collect(Collectors.toList());
    }

    private long getFilteredDoctorCount(
            List<DoctorModel> doctors,
            Set<Long> doctorIds,
            String departmentFilter) {

        if (departmentFilter == null
                || departmentFilter.isBlank()
                || departmentFilter.equalsIgnoreCase("all")) {

            return doctors.size();
        }

        return doctors.stream()
                .filter(
                        doctor
                        -> doctor.getSpecialization() != null
                        && doctor.getSpecialization()
                                .equalsIgnoreCase(
                                        departmentFilter))
                .count();
    }

    private double calculateTotalRevenue(
            List<BillingModel> bills) {

        return bills.stream()
                .mapToDouble(
                        bill
                        -> bill.getTotalAmount() != null
                        ? bill.getTotalAmount()
                        : 0.0)
                .sum();
    }

    private double calculateCompletionRate(
            List<AppointmentModel> appointments) {

        if (appointments.isEmpty()) {
            return 0.0;
        }

        long completed
                = appointments.stream()
                        .filter(
                                appointment
                                -> "COMPLETED"
                                        .equalsIgnoreCase(
                                                appointment.getStatus()))
                        .count();

        return round(
                completed * 100.0
                / appointments.size());
    }

    private double calculateAverageRating(
            List<PatientFeedbackModel> feedbackList) {

        if (feedbackList.isEmpty()) {
            return 0.0;
        }

        return round(
                feedbackList.stream()
                        .filter(
                                feedback
                                -> feedback.getRating() != null)
                        .mapToInt(
                                PatientFeedbackModel::getRating)
                        .average()
                        .orElse(0.0));
    }

    private List<ReportsAnalyticsDTO.MonthlyRevenueDTO>
            calculateMonthlyRevenue(
                    List<BillingModel> bills,
                    int year) {

        List<ReportsAnalyticsDTO.MonthlyRevenueDTO> result
                = new ArrayList<>();

        for (int month = 1;
                month <= 12;
                month++) {

            final int currentMonth
                    = month;

            double revenue
                    = bills.stream()
                            .filter(
                                    bill
                                    -> bill.getBillDate()
                                            .getMonthValue()
                                    == currentMonth)
                            .mapToDouble(
                                    bill
                                    -> bill.getTotalAmount() != null
                                    ? bill.getTotalAmount()
                                    : 0.0)
                            .sum();

            result.add(
                    new ReportsAnalyticsDTO.MonthlyRevenueDTO(
                            YearMonth.of(
                                    year,
                                    currentMonth)
                                    .getMonth()
                                    .name(),
                            round(revenue)));
        }

        return result;
    }

    private Map<String, Long> calculateAppointmentStatus(
            List<AppointmentModel> appointments) {

        Map<String, Long> result
                = new LinkedHashMap<>();

        result.put(
                "PENDING",
                countStatus(
                        appointments,
                        "PENDING"));

        result.put(
                "ACCEPTED",
                countStatus(
                        appointments,
                        "ACCEPTED"));

        result.put(
                "COMPLETED",
                countStatus(
                        appointments,
                        "COMPLETED"));

        result.put(
                "REJECTED",
                countStatus(
                        appointments,
                        "REJECTED"));

        return result;
    }

    private long countStatus(
            List<AppointmentModel> appointments,
            String status) {

        return appointments.stream()
                .filter(
                        appointment
                        -> status.equalsIgnoreCase(
                                appointment.getStatus()))
                .count();
    }

    private List<ReportsAnalyticsDTO.DepartmentPerformanceDTO>
            calculateDepartmentPerformance(
                    List<DepartmentModel> departments,
                    List<DoctorModel> doctors,
                    List<AppointmentModel> appointments,
                    List<BillingModel> bills,
                    List<PatientFeedbackModel> feedback,
                    int year,
                    String departmentFilter) {

        List<ReportsAnalyticsDTO.DepartmentPerformanceDTO> result
                = new ArrayList<>();

        List<DepartmentModel> filteredDepartments
                = departments.stream()
                        .filter(
                                department
                                -> departmentFilter == null
                                || departmentFilter.isBlank()
                                || departmentFilter.equalsIgnoreCase("all")
                                || department.getDepartmentName()
                                        .equalsIgnoreCase(
                                                departmentFilter))
                        .collect(Collectors.toList());

        Map<String, Double> departmentRevenue
                = new HashMap<>();

        double maxRevenue = 0.0;

        for (DepartmentModel department
                : filteredDepartments) {

            Set<Long> departmentDoctorIds
                    = doctors.stream()
                            .filter(
                                    doctor
                                    -> doctor.getSpecialization() != null
                                    && department.getDepartmentName() != null
                                    && doctor.getSpecialization()
                                            .equalsIgnoreCase(
                                                    department.getDepartmentName()))
                            .map(
                                    DoctorModel::getId)
                            .collect(Collectors.toSet());

            double revenue
                    = bills.stream()
                            .filter(
                                    bill
                                    -> departmentDoctorIds.contains(
                                            bill.getDoctorId()))
                            .mapToDouble(
                                    bill
                                    -> bill.getTotalAmount() != null
                                    ? bill.getTotalAmount()
                                    : 0.0)
                            .sum();

            departmentRevenue.put(
                    department.getDepartmentName(),
                    revenue);

            maxRevenue
                    = Math.max(
                            maxRevenue,
                            revenue);
        }

        for (DepartmentModel department
                : filteredDepartments) {

            Set<Long> departmentDoctorIds
                    = doctors.stream()
                            .filter(
                                    doctor
                                    -> doctor.getSpecialization() != null
                                    && department.getDepartmentName() != null
                                    && doctor.getSpecialization()
                                            .equalsIgnoreCase(
                                                    department.getDepartmentName()))
                            .map(
                                    DoctorModel::getId)
                            .collect(Collectors.toSet());

            List<AppointmentModel> departmentAppointments
                    = appointments.stream()
                            .filter(
                                    appointment
                                    -> departmentDoctorIds.contains(
                                            appointment.getDoctorId()))
                            .collect(Collectors.toList());

            Set<Long> patientIds
                    = departmentAppointments.stream()
                            .map(
                                    AppointmentModel::getPatientId)
                            .filter(
                                    id -> id != null)
                            .collect(Collectors.toSet());

            List<PatientFeedbackModel> departmentFeedback
                    = feedback.stream()
                            .filter(
                                    item
                                    -> departmentDoctorIds.contains(
                                            item.getDoctorId()))
                            .collect(Collectors.toList());

            long doctorCount
                    = departmentDoctorIds.size();

            long patientCount
                    = patientIds.size();

            long appointmentCount
                    = departmentAppointments.size();

            double revenue
                    = departmentRevenue.getOrDefault(
                            department.getDepartmentName(),
                            0.0);

            double completionRate
                    = calculateCompletionRate(
                            departmentAppointments);

            double averageRating
                    = calculateAverageRating(
                            departmentFeedback);

            double revenueScore
                    = maxRevenue > 0
                            ? (revenue / maxRevenue) * 100
                            : 0;

            double performanceScore
                    = round(
                            completionRate * 0.5
                            + (averageRating / 5.0)
                            * 100
                            * 0.3
                            + revenueScore * 0.2);

            String performance
                    = getPerformanceLabel(
                            performanceScore);

            result.add(
                    new ReportsAnalyticsDTO.DepartmentPerformanceDTO(
                            department.getDepartmentName(),
                            doctorCount,
                            patientCount,
                            appointmentCount,
                            round(revenue),
                            performanceScore,
                            performance));
        }

        return result;
    }

    private List<ReportsAnalyticsDTO.PatientGrowthDTO>
            calculatePatientGrowth(
                    List<PatientModel> patients,
                    int year,
                    String departmentFilter,
                    Set<Long> doctorIds,
                    List<AppointmentModel> appointments) {

        List<ReportsAnalyticsDTO.PatientGrowthDTO> result
                = new ArrayList<>();

        for (int month = 1;
                month <= 12;
                month++) {

            final int currentMonth
                    = month;

            long registeredPatients
                    = patients.stream()
                            .filter(
                                    patient
                                    -> patient.getRegistrationDate() != null)
                            .filter(
                                    patient
                                    -> patient.getRegistrationDate()
                                            .getYear() == year)
                            .filter(
                                    patient
                                    -> patient.getRegistrationDate()
                                            .getMonthValue()
                                    == currentMonth)
                            .count();

            if (registeredPatients == 0) {

                long appointmentPatients
                        = appointments.stream()
                                .filter(
                                        appointment
                                        -> appointment.getAppointmentDate()
                                                .getMonthValue()
                                        == currentMonth)
                                .map(
                                        AppointmentModel::getPatientId)
                                .filter(
                                        id -> id != null)
                                .collect(
                                        Collectors.toSet())
                                .size();

                registeredPatients
                        = appointmentPatients;
            }

            result.add(
                    new ReportsAnalyticsDTO.PatientGrowthDTO(
                            YearMonth.of(
                                    year,
                                    currentMonth)
                                    .getMonth()
                                    .name(),
                            registeredPatients));
        }

        return result;
    }

    private Map<String, Double>
            calculatePaymentMethodRevenue(
                    List<BillingModel> bills) {

        Map<String, Double> result
                = new LinkedHashMap<>();

        bills.stream()
                .filter(
                        bill
                        -> bill.getPaymentMethod() != null
                        && !bill.getPaymentMethod()
                                .isBlank())
                .forEach(
                        bill
                        -> result.merge(
                                bill.getPaymentMethod(),
                                bill.getPaidAmount() != null
                                ? bill.getPaidAmount()
                                : 0.0,
                                Double::sum));

        return result;
    }

    private List<ReportsAnalyticsDTO.DoctorPerformanceDTO>
            calculateDoctorPerformance(
                    List<DoctorModel> doctors,
                    List<AppointmentModel> appointments,
                    List<BillingModel> bills,
                    List<PatientFeedbackModel> feedback,
                    String departmentFilter) {

        List<ReportsAnalyticsDTO.DoctorPerformanceDTO> result
                = new ArrayList<>();

        for (DoctorModel doctor
                : doctors) {

            if (departmentFilter != null
                    && !departmentFilter.isBlank()
                    && !departmentFilter.equalsIgnoreCase("all")
                    && (doctor.getSpecialization() == null
                    || !doctor.getSpecialization()
                            .equalsIgnoreCase(
                                    departmentFilter))) {

                continue;
            }

            List<AppointmentModel> doctorAppointments
                    = appointments.stream()
                            .filter(
                                    appointment
                                    -> doctor.getId()
                                            .equals(
                                                    appointment.getDoctorId()))
                            .collect(Collectors.toList());

            Set<Long> patientIds
                    = doctorAppointments.stream()
                            .map(
                                    AppointmentModel::getPatientId)
                            .filter(
                                    id -> id != null)
                            .collect(Collectors.toSet());

            double revenue
                    = bills.stream()
                            .filter(
                                    bill
                                    -> doctor.getId()
                                            .equals(
                                                    bill.getDoctorId()))
                            .mapToDouble(
                                    bill
                                    -> bill.getTotalAmount() != null
                                    ? bill.getTotalAmount()
                                    : 0.0)
                            .sum();

            List<PatientFeedbackModel> doctorFeedback
                    = feedback.stream()
                            .filter(
                                    item
                                    -> doctor.getId()
                                            .equals(
                                                    item.getDoctorId()))
                            .collect(Collectors.toList());

            double rating
                    = calculateAverageRating(
                            doctorFeedback);

            result.add(
                    new ReportsAnalyticsDTO.DoctorPerformanceDTO(
                            doctor.getId(),
                            doctor.getDoctorName(),
                            doctor.getSpecialization(),
                            patientIds.size(),
                            doctorAppointments.size(),
                            rating,
                            round(revenue)));
        }

        result.sort(
                Comparator.comparing(
                        ReportsAnalyticsDTO.DoctorPerformanceDTO::getAppointmentCount)
                        .reversed());

        return result;
    }

    private List<ReportsAnalyticsDTO.WeeklyAppointmentStatusDTO>
            calculateWeeklyAppointmentStatus(
                    List<AppointmentModel> appointments) {

        LocalDate today
                = LocalDate.now();

        LocalDate weekStart
                = today.with(
                        TemporalAdjusters
                                .previousOrSame(
                                        DayOfWeek.MONDAY));

        List<ReportsAnalyticsDTO.WeeklyAppointmentStatusDTO> result
                = new ArrayList<>();

        for (int i = 0; i < 7; i++) {

            LocalDate date
                    = weekStart.plusDays(i);

            List<AppointmentModel> dayAppointments
                    = appointments.stream()
                            .filter(
                                    appointment
                                    -> date.equals(
                                            appointment
                                                    .getAppointmentDate()))
                            .collect(Collectors.toList());

            long completed
                    = countStatus(
                            dayAppointments,
                            "COMPLETED");

            long cancelled
                    = countStatus(
                            dayAppointments,
                            "REJECTED");

            long pending
                    = countStatus(
                            dayAppointments,
                            "PENDING");

            result.add(
                    new ReportsAnalyticsDTO.WeeklyAppointmentStatusDTO(
                            date.getDayOfWeek()
                                    .name(),
                            completed,
                            cancelled,
                            pending));
        }

        return result;
    }

    private String getPerformanceLabel(
            double score) {

        if (score >= 80) {
            return "Excellent";
        }

        if (score >= 60) {
            return "Good";
        }

        return "Average";
    }

    private String buildAnalyticsInsight(
            ReportsAnalyticsDTO dto) {

        if (dto.getTotalAppointments() == 0) {
            return "No appointment data is available for the selected period.";
        }

        return String.format(
                "The selected period contains %,d appointments, %,d patients and %,d doctors. "
                + "The appointment completion rate is %.1f%% and total billed revenue is ₹%.2f.",
                dto.getTotalAppointments(),
                dto.getTotalPatients(),
                dto.getTotalDoctors(),
                dto.getAppointmentCompletionRate(),
                dto.getTotalRevenue());
    }

    private double round(
            double value) {

        return Math.round(
                value * 100.0)
                / 100.0;
    }
}
