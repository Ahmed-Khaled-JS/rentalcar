package com.example.demo.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.dto.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Request;
import com.example.demo.Model.Request.RequestStatus;
import com.example.demo.Repository.RequestRepo;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RequestService {
        private final RestTemplate restTemplate;


    
        @Autowired
        RequestRepo requestRepo;
    
        public boolean checkRequest(Request r) {
            Date from = r.getFrom();
            Date to = r.getTo();
            String vehicleServiceUrl = "http://localhost:8081/api/vehicle/getCarById/" + r.getVehicle();
            ResponseEntity<Boolean> isAvaliable = restTemplate.getForEntity(vehicleServiceUrl, Boolean.class);
            if (from.before(to) && isAvaliable.getBody()) {
                return true;
            } else if (from.before(to) && !isAvaliable.getBody()) {
                if (requestRepo.existsByDateRangeAndVehicleId(from, to, r.getVehicle())) {
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
    public List<Vehicle> getVehiclesOfServiceProvider(Integer serviceProviderId) {
        String vehicleServiceUrl = "http://localhost:8081/api/vehicle/ownerVehicles/"+serviceProviderId;
        try {

            ResponseEntity<Vehicle> responseEntity = restTemplate.getForEntity(vehicleServiceUrl, Vehicle.class, serviceProviderId);
            return (List<Vehicle>) responseEntity.getBody();
        } catch (RestClientResponseException ex) {
            throw new RuntimeException("Failed to get vehicles of service provider", ex);
        }

    }

}
