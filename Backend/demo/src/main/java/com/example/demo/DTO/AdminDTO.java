package com.example.demo.DTO;

public class AdminDTO {

    private Long adminId;
    private String fullName;
    private String email;
    private Long mobileNumber;
    private String dob;
    private String gender;
    private String address;
    private String profileImage;

    public AdminDTO() {
    }

    public AdminDTO(
            Long adminId,
            String fullName,
            String email,
            Long mobileNumber,
            String dob,
            String gender,
            String address,
            String profileImage) {

        this.adminId = adminId;
        this.fullName = fullName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.profileImage = profileImage;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
