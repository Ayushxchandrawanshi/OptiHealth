package com.example.demo.DTO;

public class DoctorDTO {

    private Long id;
    private String doctorName;
    private String email;
    private String specialization;
    private int experience;
    private float fee;
    private String description;
    private String status;

    public DoctorDTO() {
    }

    public DoctorDTO(Long id, String doctorName, String email,
            String specialization, int experience,
            float fee, String description, String status) {

        this.id = id;
        this.doctorName = doctorName;
        this.email = email;
        this.specialization = specialization;
        this.experience = experience;
        this.fee = fee;
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
