package com.personal.jobportal.job;

import java.time.LocalDate;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // This annotation marks the class as a JPA entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) 
    private String title;

    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private String company;
    
    @Column(nullable = false)
    private LocalDate postedDate = LocalDate.now();


    /* ------------------------- NO ARGUMENT CONSTRUCTOR ------------------------ */
    public Job() {
        // Default constructor for JPA
    }

    /* ------------------------- ALL ARGUMENT CONSTRUCTOR ------------------------ */
    public Job(String title, String description, String company, LocalDate postedDate) {
        this.title = title;
        this.description = description;
        this.company = company;
    }

    
    /* --------------------------- GETTERS AND SETTERS -------------------------- */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDate getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDate postedDate) {
        this.postedDate = postedDate;
    }

    














    
}
