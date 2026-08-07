package com.example.demo.DTO;

public class PatientDashboardDTO {

    private String fullName;

    private String email;

    private Long mobileNumber;

    private int appointmentCount;

    private int doctorCount;

    private int reportCount;

    private int prescriptionCount;

    private String nextDoctorName;

    public PatientDashboardDTO() {
    }

    public PatientDashboardDTO(String fullName,
            String email,
            Long mobileNumber,
            int appointmentCount,
            int doctorCount,
            int reportCount,
            int prescriptionCount,
            String nextDoctorName) {

        this.fullName = fullName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.appointmentCount = appointmentCount;
        this.doctorCount = doctorCount;
        this.reportCount = reportCount;
        this.prescriptionCount = prescriptionCount;
        this.nextDoctorName = nextDoctorName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getAppointmentCount() {
        return appointmentCount;
    }

    public void setAppointmentCount(int appointmentCount) {
        this.appointmentCount = appointmentCount;
    }

    public int getDoctorCount() {
        return doctorCount;
    }

    public void setDoctorCount(int doctorCount) {
        this.doctorCount = doctorCount;
    }

    public int getReportCount() {
        return reportCount;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }

    public int getPrescriptionCount() {
        return prescriptionCount;
    }

    public void setPrescriptionCount(int prescriptionCount) {
        this.prescriptionCount = prescriptionCount;
    }

    public String getNextDoctorName() {
        return nextDoctorName;
    }

    public void setNextDoctorName(String nextDoctorName) {
        this.nextDoctorName = nextDoctorName;
    }

}
