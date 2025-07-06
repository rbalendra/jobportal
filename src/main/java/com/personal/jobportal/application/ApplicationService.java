package com.personal.jobportal.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.jobportal.job.Job;
import com.personal.jobportal.job.JobRepository;
import com.personal.jobportal.user.User;
import com.personal.jobportal.user.UserRepository;

@Service
public class ApplicationService {

    // Injecting repositories to access User and Job data
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;


    // Method to apply for a job
    // This method will create a new Application entity and save it to the database
    // fetch the user object from the userRepository using userId or throw an exception if not found
    public Application applyForJob(Long userId, Long jobId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // fetch the job object from the jobRepository using jobId or throw an exception if not found
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

        // Create a new Application object 
        // this object will hold the user and job information
        Application application = new Application(user, job);

        // Save the application to the database
        return applicationRepository.save(application);
    }

}
