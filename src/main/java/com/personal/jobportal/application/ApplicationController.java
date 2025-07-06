package com.personal.jobportal.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    // only logged in users can apply for job
    @PostMapping("/apply/{userId}/{jobId}")
    public ResponseEntity<Application> applyForJob(
        @PathVariable Long userId,
        @PathVariable Long jobId) {
        return ResponseEntity.ok(applicationService.applyForJob(userId, jobId));
    }
}
