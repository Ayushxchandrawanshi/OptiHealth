package com.example.demo.DTO;

public class AdminDTO {

    private Long adminId;
    private String fullName;
    private String email;
    private Long mobileNumber;

    public AdminDTO() {
    }

    public AdminDTO(Long adminId, String fullName, String email, Long mobileNumber) {
        this.adminId = adminId;
        this.fullName = fullName;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
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

}
