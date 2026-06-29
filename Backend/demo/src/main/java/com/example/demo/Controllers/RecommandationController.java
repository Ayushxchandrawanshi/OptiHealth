package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.DoctorModel;
import com.example.demo.Repository.DoctorRepo;
import com.example.demo.Utils.AskAi;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/recommandation")
public class RecommandationController {

    @Autowired
    AskAi askAi;

    @Autowired
    DoctorRepo doctorRepo;

    @GetMapping("/byAi")
    public DoctorModel getRecommandation(@RequestParam String q) {
        String Disease = q;
        Long Id = (long) askAi.getRecommandation(Disease);

        if (Id != -1) {
            DoctorModel doctor = doctorRepo.findById(Id).get();
            return doctor;
        }

        return null;

    }
}
