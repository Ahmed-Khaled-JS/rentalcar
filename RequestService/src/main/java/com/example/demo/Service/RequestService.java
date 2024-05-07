package com.example.demo.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.demo.dto.VehicleApiResponse;
import com.example.demo.dto.VehicleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
            if (from.before(to) && isAvaliable.getBody() && !requestRepo.existsByDateRangeAndVehicleId(from, to, r.getVehicle())) {
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
    public void acceptRequest(Integer requestId) {
        // Logic to update request status to accepted
        Request request = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(RequestStatus.ACCEPTED);
        requestRepo.save(request);
        String VEHICLE_SERVICE_BASE_URL = "http://localhost:8081/api/vehicle/";
        ResponseEntity<Void> response = restTemplate.exchange(
                VEHICLE_SERVICE_BASE_URL +  request.getVehicle() + "/unavailable",
                HttpMethod.PUT,
                null,
                Void.class
        );
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to update vehicle availability to true");
        }
    }

    public void rejectRequest(Integer requestId) {
        // Logic to update request status to rejected
        Request request = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(RequestStatus.REJECTED);
        requestRepo.save(request);

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
    public List<List<Request>> getVehiclesOfServiceProvider(Integer serviceProviderId) {
        String vehicleServiceUrl = "http://localhost:8081/api/vehicle/ownerVehicles/" + serviceProviderId; // Replace with the actual URL of the vehicle service
        try {
            ResponseEntity<VehicleApiResponse> responseEntity = restTemplate.getForEntity(vehicleServiceUrl, VehicleApiResponse.class);
            List<List<Request>> requests = new java.util.ArrayList<>(List.of());
            for (VehicleResponse r : Objects.requireNonNull(responseEntity.getBody()).getResult()){
                List<Request> req = requestRepo.findAllByVehicleidAndRequestStatus(r.getCarTd(), Request.RequestStatus.UNDER_REVIEW);

                requests.add(req);
            }
            return requests;

        } catch (RestClientResponseException ex) {
            // Handle RestClientResponseException, e.g., log the error
            // You can rethrow this exception or return a failure response
            throw new RuntimeException("Failed to get vehicles of service provider", ex);
        }
    }



}
