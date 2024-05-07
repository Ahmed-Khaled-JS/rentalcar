package com.feedback1.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.feedback1.demo.model.Feedback;

import com.feedback1.demo.service.FeedbackService;


;




@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    
    @PostMapping("/makefeedback")
    public ResponseEntity<String> makeRequest(@RequestBody Map<String,String>feedback) {
      
        try{
                String desc=feedback.get("description");
                Integer carId = Integer.parseInt(feedback.get("car_id"));
                Integer userId = Integer.parseInt(feedback.get("user_id"));
                Feedback f=new Feedback();
                f.setDescription(desc);f.setUser(userId);f.setVehicle(carId);
                Boolean ValidFeedback = feedbackService.makeFeedback(f);
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
    @GetMapping("/getfeedback")
    public List<Feedback> getFeedback() {
        return feedbackService.getFeedback();
    }
    @GetMapping("/getFeedbackByCarId/{carId}")
    public List<Feedback> getFeedbackByCarId(@PathVariable Integer carId) {
        return feedbackService.getFeedbackByCarId(carId);
    }


}
