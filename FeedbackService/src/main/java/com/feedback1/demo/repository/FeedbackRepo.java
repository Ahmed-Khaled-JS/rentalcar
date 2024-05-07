package com.feedback1.demo.repository;


import org.springframework.data.repository.CrudRepository;

import com.feedback1.demo.model.Feedback;

import java.util.List;


public interface FeedbackRepo extends CrudRepository<Feedback,Integer> {
    List<Feedback> findByVehicleId(int carId);
    List<Feedback> findAll();
}
