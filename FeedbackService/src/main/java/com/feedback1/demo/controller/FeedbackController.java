package com.feedback1.demo.controller;

import java.util.Map;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feedback1.demo.model.Feedback;

import com.feedback1.demo.service.FeedbackService;


import com.feedback1.demo.model.User;

import com.feedback1.demo.model.Vehicle;

import com.feedback1.demo.repository.UserRepository;
import com.feedback1.demo.repository.VehicleRepo;;




@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @Autowired 
    VehicleRepo  vehicleRepo;

    
    @Autowired 
    UserRepository  userRepo;
    
 @PostMapping("/makefeedback")
    public ResponseEntity<String> makeRequest(@RequestBody Map<String,String>feedback) {
      
        try{
                String desc=feedback.get("description");
                String model= feedback.get("model");
                Feedback f=new Feedback();
                Optional<Vehicle> v = vehicleRepo.findBycarName(model);
                Optional<User> u = userRepo.findById(3);//mehataa id el consumer haggebo men el token wala eh?

                if (v.isPresent() && u.isPresent()) {
                    Vehicle vv = v.get();
                    User uu=u.get(); 
                    f.setDescription(desc);f.setUser(uu);f.setVehicle(vv);
                }  else {
                    return ResponseEntity.badRequest().body("No vehicle with this model is Found");
                   }

                      boolean ValidFeedback = feedbackService.makeFeedback(f);
            if ( ValidFeedback ) {
                return ResponseEntity.ok("Feedback submitted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("could not record your feedback,try again! ");
            }
            
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("please enter data in a valid format"+e);
        }
            
    }

}
