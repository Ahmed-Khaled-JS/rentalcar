package com.example.demo.Model;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name="request")
public class Request {

 
   
    @Id
    @Column(name="request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer requestId;


 
     public enum RequestStatus {
 
         UNDER_REVIEW,
         ACCEPTED,
         REJECTED
     }
 
     @Column(name="status")
     @Enumerated(EnumType.STRING) // this annoation to tell spring that role is an enum
     private RequestStatus requestStatus ;
 
 
    
     //@NotNull(message = "rental start date is required")
     @Column(name="rental_start_date")
     @DateTimeFormat(pattern="yyyy-MM-dd")
     private Date from;
 
     //@NotNull(message = "rental end date is required")
     @Column(name="rental_end_date")
     @DateTimeFormat(pattern="yyyy-MM-dd")
     private Date to;
 
//     @ManyToOne
//     @JoinColumn(name = "user_id")
     private Integer userid;
 

     private Integer vehicleid;
 
 
     @CreationTimestamp
     private LocalDateTime createdAt;
     
     @UpdateTimestamp
     private LocalDateTime updatedAt;
 
     public RequestStatus getRequestStatus() {
         return requestStatus;
     }
     public void setRequestStatus(RequestStatus requestStatus) {
         this.requestStatus = requestStatus;
     }
     public LocalDateTime getCreatedAt() {
         return createdAt;
     }
     public void setCreatedAt(LocalDateTime createdAt) {
         this.createdAt = createdAt;
     }
     public LocalDateTime getUpdatedAt() {
         return updatedAt;
     }
     public void setUpdatedAt(LocalDateTime updatedAt) {
         this.updatedAt = updatedAt;
     }
     public Request(){
 
     }
     public Request(RequestStatus status, Date from, Date to, Integer user, Integer vehicle,Long totalPrice) {
         this.requestStatus = status;
         this.from = from;
         this.to = to;
         this.userid = user;
         this.vehicleid = vehicle;
     }//da ely el mafrood yet3emel
//
//     public Request(Integer requestId,RequestStatus status, Date from, Date to, Integer user, Integer vehicle) {
//         this.requestId = requestId;
//         this.requestStatus = status;
//         this.from = from;
//         this.to = to;
//         this.userid = user;
//         this.vehicleid = vehicle;
//     }
 
     public Integer getRequestId() {
         return requestId;
     }
 
     public void setRequestId(Integer requestId) {
         this.requestId = requestId;
     }
 
     public Date getFrom() {
         return from;
     }
 
     public void setFrom(Date from) {
         this.from = from;
     }
 
     public Date getTo() {
         return to;
     }
 
     public void setTo(Date to) {
         this.to = to;
     }
     
    
     public Integer getUser() {
         return userid;
     }
 
     public void setUser(Integer user) {
         this.userid = user;
     }
 
     public Integer getVehicle() {
         return vehicleid;
     }
 
     public void setVehicle(Integer vehicle) {
         this.vehicleid = vehicle;
     }
     public RequestStatus getStatus() {
         return requestStatus;
     }
     public void setStatus(RequestStatus status) {
         this.requestStatus = status;
     }
    
}
