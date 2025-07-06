package com.personal.jobportal.application;

import com.personal.jobportal.job.Job;
import com.personal.jobportal.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "applications") // Assuming you want to create a table named 'application'
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;


    /* ------------------------- NO ARGUMENT CONSTRUCTOR ------------------------ */
    public Application() {
        // Default constructor for JPA
    }
    
    /* ------------------------- ALL ARGUMENT CONSTRUCTOR ------------------------ */
    public Application(User user, Job job) {
        this.user = user;
        this.job = job;
    }

    /* ------------------------- GETTERS AND SETTERS ------------------------ */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }





    

}
