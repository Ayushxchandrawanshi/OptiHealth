package com.example.demo.Utils;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.Models.DoctorModel;
import com.example.demo.Repository.DoctorRepo;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import jakarta.annotation.PostConstruct;

@Component
public class AskAi {

    private final DoctorRepo doctorRepo;
    private String doctorDetails = "";

    public AskAi(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    @PostConstruct
    private void init() {
        List<DoctorModel> doctors = doctorRepo.findAll();
        for (DoctorModel doctor : doctors) {
            doctorDetails += "Specialization" + doctor.getSpecialization() + "\n";
            doctorDetails += "Experiance" + doctor.getExperience() + "\n";
            doctorDetails += "Description" + doctor.getDescription() + "\n";
            doctorDetails += "Id" + doctor.getId() + "\n";
        }
    }

    public int getRecommandation(String disease) {
        Client client = Client.builder()
                .apiKey("AIzaSyDXW8SBYKK3PSfsHBZ0atnFosNzD7U-bdM")
                .build();

        GenerateContentResponse response = client.models.generateContent(
                "gemini-2.5-flash",
                "You are a smart healthcare recommender. Analyze the disease and suggest the best doctor.\n"
                + "Doctors: " + doctorDetails
                + "Disease: " + disease
                + "Suggest the best doctor for this disease. ONLY RETURN THE ID, NO TEXT OR SYMBOLS.",
                null
        );

        String output = response.text();
        if (output != null) {
            return Integer.parseInt(output.trim());
        }

        return -1;
    }

}
