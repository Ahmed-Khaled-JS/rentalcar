package com.example.demo.AOP;

import java.util.Optional;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Model.Request;
import com.example.demo.Model.Request.RequestStatus;
import com.example.demo.Repository.RequestRepo;

@Component
@Aspect
public class RequestAop {

    @Autowired
    RequestRepo requestRepo;


 

    @Before("execution(* com.example.demo.Service.RequestService.updateVehicleRequest(..)) && args(r,id)")
    public Object checkRequestStatusWithAopUpdate(Request r,Integer id) {

        Optional<Request> optionalReq = requestRepo.findById(id);

        if (optionalReq.isPresent()) {
            Request existingreq = optionalReq.get();
            if (existingreq.getStatus() == RequestStatus.UNDER_REVIEW) {
                return true;// can be updated
            } else {
                throw new IllegalStateException("Request is not under review AOP");
            }
        } else {
            throw new IllegalArgumentException("Request with id " + id + " not found. AOP");
        }

    }


    @Before("execution(* com.example.demo.Service.RequestService.deleteVehicleRequest(..)) && args(id)")
    public Object checkRequestStatusWithAopDelete(Integer id) {

        Optional<Request> optionalReq = requestRepo.findById(id);

        if (optionalReq.isPresent()) {
            Request existingreq = optionalReq.get();
            if (existingreq.getStatus() == RequestStatus.UNDER_REVIEW) {
                return true;// can be updated
            } else {
                throw new IllegalStateException("Request is not under review AOP");
            }
        } else {
            throw new IllegalArgumentException("Request with id " + id + " not found . AOP");
        }

    }
   
}
