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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Vehicle vehicle;


    @CreationTimestamp
    private LocalDateTime createdAt;


    public Feedback(){

    }
    
    public Feedback( String description, User user, Vehicle vehicle, LocalDateTime createdAt) {
        this.description = description;
        this.user = user;
        this.vehicle = vehicle;
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


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public Vehicle getVehicle() {
        return vehicle;
    }


    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }



    
}
