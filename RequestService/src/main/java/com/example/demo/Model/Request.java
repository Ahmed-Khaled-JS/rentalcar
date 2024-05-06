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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name="request")
public class Request {

 
   
    @Id
    @Column(name="request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer requestId;
 
    
    @Column(name="total_price")
    private Long totalPrice;
 
 
     public enum RequestStatus {
 
         UNDER_REVIEW,
         ACCEPTED
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
 
     @ManyToOne
     @JoinColumn(name = "user_id")
     private User user;
 
     @ManyToOne
     @JoinColumn(name = "car_id")
     private Vehicle vehicle;
 
 
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
     public Request(RequestStatus status, Date from, Date to, User user, Vehicle vehicle,Long totalPrice) {
         this.requestStatus = status;
         this.from = from;
         this.to = to;
         this.user = user;
         this.vehicle = vehicle;
         this.totalPrice=totalPrice;
     }//da ely el mafrood yet3emel 
    
     public Request(Integer requestId,RequestStatus status, Date from, Date to, User user, Vehicle vehicle) {
         this.requestId = requestId;
         this.requestStatus = status;
         this.from = from;
         this.to = to;
         this.user = user;
         this.vehicle = vehicle;
     }
 
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
     public RequestStatus getStatus() {
         return requestStatus;
     }
     public void setStatus(RequestStatus status) {
         this.requestStatus = status;
     }
     public Long getTotalPrice() {
         return totalPrice;
     }
     public void setTotalPrice(Long totalPrice) {
         this.totalPrice = totalPrice;
     }
    
}
