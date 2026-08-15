package com.example.demo.DTO;

import java.util.List;
import java.util.Map;

public class ReportsAnalyticsDTO {

    private double totalRevenue;
    private long totalPatients;
    private long totalAppointments;
    private long totalDoctors;

    private double appointmentCompletionRate;
    private double patientSatisfaction;

    private List<MonthlyRevenueDTO> monthlyRevenue;

    private Map<String, Long> appointmentStatus;

    private List<DepartmentPerformanceDTO> departmentPerformance;

    private List<PatientGrowthDTO> patientGrowth;

    private Map<String, Double> paymentMethodRevenue;

    private List<DoctorPerformanceDTO> doctorPerformance;

    private List<WeeklyAppointmentStatusDTO> weeklyAppointmentStatus;

    private String analyticsInsight;

    public ReportsAnalyticsDTO() {
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public long getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(long totalPatients) {
        this.totalPatients = totalPatients;
    }

    public long getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(long totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    public long getTotalDoctors() {
        return totalDoctors;
    }

    public void setTotalDoctors(long totalDoctors) {
        this.totalDoctors = totalDoctors;
    }

    public double getAppointmentCompletionRate() {
        return appointmentCompletionRate;
    }

    public void setAppointmentCompletionRate(
            double appointmentCompletionRate) {

        this.appointmentCompletionRate
                = appointmentCompletionRate;
    }

    public double getPatientSatisfaction() {
        return patientSatisfaction;
    }

    public void setPatientSatisfaction(
            double patientSatisfaction) {

        this.patientSatisfaction
                = patientSatisfaction;
    }

    public List<MonthlyRevenueDTO> getMonthlyRevenue() {
        return monthlyRevenue;
    }

    public void setMonthlyRevenue(
            List<MonthlyRevenueDTO> monthlyRevenue) {

        this.monthlyRevenue = monthlyRevenue;
    }

    public Map<String, Long> getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(
            Map<String, Long> appointmentStatus) {

        this.appointmentStatus = appointmentStatus;
    }

    public List<DepartmentPerformanceDTO>
            getDepartmentPerformance() {

        return departmentPerformance;
    }

    public void setDepartmentPerformance(
            List<DepartmentPerformanceDTO> departmentPerformance) {

        this.departmentPerformance
                = departmentPerformance;
    }

    public List<PatientGrowthDTO> getPatientGrowth() {
        return patientGrowth;
    }

    public void setPatientGrowth(
            List<PatientGrowthDTO> patientGrowth) {

        this.patientGrowth = patientGrowth;
    }

    public Map<String, Double>
            getPaymentMethodRevenue() {

        return paymentMethodRevenue;
    }

    public void setPaymentMethodRevenue(
            Map<String, Double> paymentMethodRevenue) {

        this.paymentMethodRevenue
                = paymentMethodRevenue;
    }

    public List<DoctorPerformanceDTO>
            getDoctorPerformance() {

        return doctorPerformance;
    }

    public void setDoctorPerformance(
            List<DoctorPerformanceDTO> doctorPerformance) {

        this.doctorPerformance
                = doctorPerformance;
    }

    public List<WeeklyAppointmentStatusDTO>
            getWeeklyAppointmentStatus() {

        return weeklyAppointmentStatus;
    }

    public void setWeeklyAppointmentStatus(
            List<WeeklyAppointmentStatusDTO> weeklyAppointmentStatus) {

        this.weeklyAppointmentStatus
                = weeklyAppointmentStatus;
    }

    public String getAnalyticsInsight() {
        return analyticsInsight;
    }

    public void setAnalyticsInsight(
            String analyticsInsight) {

        this.analyticsInsight
                = analyticsInsight;
    }

    public static class MonthlyRevenueDTO {

        private String month;
        private double revenue;

        public MonthlyRevenueDTO() {
        }

        public MonthlyRevenueDTO(
                String month,
                double revenue) {

            this.month = month;
            this.revenue = revenue;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public double getRevenue() {
            return revenue;
        }

        public void setRevenue(double revenue) {
            this.revenue = revenue;
        }
    }

    public static class DepartmentPerformanceDTO {

        private String departmentName;
        private long doctorCount;
        private long patientCount;
        private long appointmentCount;
        private double revenue;
        private double performanceScore;
        private String performance;

        public DepartmentPerformanceDTO() {
        }

        public DepartmentPerformanceDTO(
                String departmentName,
                long doctorCount,
                long patientCount,
                long appointmentCount,
                double revenue,
                double performanceScore,
                String performance) {

            this.departmentName
                    = departmentName;

            this.doctorCount
                    = doctorCount;

            this.patientCount
                    = patientCount;

            this.appointmentCount
                    = appointmentCount;

            this.revenue
                    = revenue;

            this.performanceScore
                    = performanceScore;

            this.performance
                    = performance;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(
                String departmentName) {

            this.departmentName
                    = departmentName;
        }

        public long getDoctorCount() {
            return doctorCount;
        }

        public void setDoctorCount(
                long doctorCount) {

            this.doctorCount
                    = doctorCount;
        }

        public long getPatientCount() {
            return patientCount;
        }

        public void setPatientCount(
                long patientCount) {

            this.patientCount
                    = patientCount;
        }

        public long getAppointmentCount() {
            return appointmentCount;
        }

        public void setAppointmentCount(
                long appointmentCount) {

            this.appointmentCount
                    = appointmentCount;
        }

        public double getRevenue() {
            return revenue;
        }

        public void setRevenue(
                double revenue) {

            this.revenue
                    = revenue;
        }

        public double getPerformanceScore() {
            return performanceScore;
        }

        public void setPerformanceScore(
                double performanceScore) {

            this.performanceScore
                    = performanceScore;
        }

        public String getPerformance() {
            return performance;
        }

        public void setPerformance(
                String performance) {

            this.performance
                    = performance;
        }
    }

    public static class PatientGrowthDTO {

        private String month;
        private long patientCount;

        public PatientGrowthDTO() {
        }

        public PatientGrowthDTO(
                String month,
                long patientCount) {

            this.month = month;
            this.patientCount
                    = patientCount;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public long getPatientCount() {
            return patientCount;
        }

        public void setPatientCount(
                long patientCount) {

            this.patientCount
                    = patientCount;
        }
    }

    public static class DoctorPerformanceDTO {

        private Long doctorId;
        private String doctorName;
        private String department;
        private long patientCount;
        private long appointmentCount;
        private double rating;
        private double revenue;

        public DoctorPerformanceDTO() {
        }

        public DoctorPerformanceDTO(
                Long doctorId,
                String doctorName,
                String department,
                long patientCount,
                long appointmentCount,
                double rating,
                double revenue) {

            this.doctorId
                    = doctorId;

            this.doctorName
                    = doctorName;

            this.department
                    = department;

            this.patientCount
                    = patientCount;

            this.appointmentCount
                    = appointmentCount;

            this.rating
                    = rating;

            this.revenue
                    = revenue;
        }

        public Long getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(
                Long doctorId) {

            this.doctorId
                    = doctorId;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(
                String doctorName) {

            this.doctorName
                    = doctorName;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(
                String department) {

            this.department
                    = department;
        }

        public long getPatientCount() {
            return patientCount;
        }

        public void setPatientCount(
                long patientCount) {

            this.patientCount
                    = patientCount;
        }

        public long getAppointmentCount() {
            return appointmentCount;
        }

        public void setAppointmentCount(
                long appointmentCount) {

            this.appointmentCount
                    = appointmentCount;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(
                double rating) {

            this.rating
                    = rating;
        }

        public double getRevenue() {
            return revenue;
        }

        public void setRevenue(
                double revenue) {

            this.revenue
                    = revenue;
        }
    }

    public static class WeeklyAppointmentStatusDTO {

        private String day;
        private long completed;
        private long cancelled;
        private long pending;

        public WeeklyAppointmentStatusDTO() {
        }

        public WeeklyAppointmentStatusDTO(
                String day,
                long completed,
                long cancelled,
                long pending) {

            this.day
                    = day;

            this.completed
                    = completed;

            this.cancelled
                    = cancelled;

            this.pending
                    = pending;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public long getCompleted() {
            return completed;
        }

        public void setCompleted(
                long completed) {

            this.completed
                    = completed;
        }

        public long getCancelled() {
            return cancelled;
        }

        public void setCancelled(
                long cancelled) {

            this.cancelled
                    = cancelled;
        }

        public long getPending() {
            return pending;
        }

        public void setPending(
                long pending) {

            this.pending
                    = pending;
        }
    }
}
