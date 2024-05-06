package com.example.demo.Service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Request;
import com.example.demo.Model.Request.RequestStatus;
import com.example.demo.Repository.RequestRepo;
import com.example.demo.Repository.VehicleRepo;

@Service
public class RequestService {


        @Autowired
        VehicleRepo vehicleRepo;
    
        @Autowired
        RequestRepo requestRepo;
    
        public boolean checkRequest(Request r) {
            Date from = r.getFrom();
            Date to = r.getTo();
    
            if (from.before(to) && (r.getVehicle().getIsAvaliable())) {
                return true;
            } else if (from.before(to) && (!r.getVehicle().getIsAvaliable())) {
    
                if (requestRepo.existsByDateRangeAndVehicleId(from, to, r.getVehicle().getCarTd())) {
    
                    return false;
                } else {
                    return true;
                }
            }
            return false;
    
        }
    
        public boolean makeRequest(Request r) {// make request
    
            if (checkRequest(r)) {
                requestRepo.save(r);
                return true;
            }
    
            return false;
        }
    
    public boolean checkRequestStatus( Integer id){
        Optional<Request> optionalReq = requestRepo.findById(id);
    
        if (optionalReq.isPresent()) {                                             // requestid mawgood
            Request existingreq = optionalReq.get();
            if (existingreq.getStatus() == RequestStatus.UNDER_REVIEW) {
                return true;//can be updated 
            }
            else {
                return false;
            }
    
    
    }
    return false;
    }
    
        public boolean updateVehicleRequest(Request r, Integer id) {// updateRequest
    
            Optional<Request> optionalReq = requestRepo.findById(id);
    
            if (optionalReq.isPresent() && checkRequest(r)) {// el request not overlapping,start wa el end mazboot +en el
                                                             // requestid mawgood
                Request existingreq = optionalReq.get();
              
                    existingreq.setFrom(r.getFrom());
                    existingreq.setTo(r.getTo());
                    existingreq.setVehicle(r.getVehicle());
                    existingreq.setTotalPrice(r.getTotalPrice());
                    requestRepo.save(existingreq);
                    return true;
                }
              
    
            
    
            return false;
    
        }
    
       public boolean requestWithThisId(Integer id){
    
        Optional<Request> optionalReq = requestRepo.findById(id);
        if (optionalReq.isPresent()){
            return true;
        }
    
        return false;
        }
    
    
        public boolean deleteVehicleRequest( Integer id) {
           if (checkRequestStatus(id)){
            requestRepo.deleteById(id);
            return true;
           }
    
    
            return false;
        }
    

}
