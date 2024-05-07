package com.feedback1.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feedback1.demo.model.Feedback;
import com.feedback1.demo.repository.FeedbackRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    @Autowired
    private final FeedbackRepo feedbackRepo;
    
    public  boolean makeFeedback(Feedback f) {
     if (f.getDescription().length()>1){
     feedbackRepo.save(f);
        return true;
     }
 
        return false;
    }


    public List<Feedback> getFeedback() {
        List<Feedback> allFeedback = feedbackRepo.findAll();
        return allFeedback;
    }

    public List<Feedback> getFeedbackByCarId(Integer carId) {
        List<Feedback> allFeedbackbycarId = feedbackRepo.findByVehicleId(carId);
        return allFeedbackbycarId;
    }
}
