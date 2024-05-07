package com.feedback1.demo.model;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name="feedback")
public class Feedback {

   @Id
   @Column(name="feedback_id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer feedbackId;

   
   @Column(name="description")
   private String  description;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="car_id")
    private Integer vehicleId;


    @CreationTimestamp
    private LocalDateTime createdAt;


    public Feedback(){

    }
    
    public Feedback( String description, Integer user, Integer vehicle, LocalDateTime createdAt) {
        this.description = description;
        this.userId = user;
        this.vehicleId = vehicle;
        this.createdAt = createdAt;
    }


    public Integer getFeedbackId() {
        return feedbackId;
    }


    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getUser() {
        return userId;
    }


    public void setUser(Integer user) {
        this.userId = user;
    }


    public Integer getVehicle() {
        return vehicleId;
    }


    public void setVehicle(Integer vehicle) {
        this.vehicleId = vehicle;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }



    
}
