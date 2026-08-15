package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.ReportsAnalyticsDTO;
import com.example.demo.Services.ReportsAnalyticsService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/reports")
public class ReportsAnalyticsController {

    @Autowired
    private ReportsAnalyticsService reportsAnalyticsService;

    @GetMapping("/analytics")
    public ReportsAnalyticsDTO getAnalytics(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String department) {

        return reportsAnalyticsService.getAnalytics(
                year,
                department);
    }
}
