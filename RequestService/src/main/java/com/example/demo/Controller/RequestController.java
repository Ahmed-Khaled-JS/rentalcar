package com.example.demo.Controller;

import com.example.demo.dto.Vehicle;
import com.example.demo.dto.VehicleApiResponse;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.Request;
import com.example.demo.Model.Request.RequestStatus;
import com.example.demo.Service.RequestService;

import java.util.List;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/request")
public class RequestController {

  //  @Autowired
  //   RequestValidatyService requestValidatyService;

    @Autowired
    RequestService requestService;



    @PostMapping("/makeRequest")
    public ResponseEntity<String> makeRequest(@RequestBody Map<String,String>request) {


       String startstr= request.get("from");
       String endstr= request.get("to");
       Integer carid= Integer.parseInt(request.get("carid"));
       Integer userid= Integer.parseInt(request.get("userid"));

       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start, end;
        try {
            Request r=new Request();
            start = dateFormat.parse(startstr);
            end = dateFormat.parse(endstr);
            r.setFrom(start); r.setTo(end); r.setVehicle(carid); r.setUser(userid); r.setStatus(RequestStatus.UNDER_REVIEW);
            boolean requestValid = requestService.makeRequest(r);
            if (requestValid) {
              //+ jwtService.extractUsername(jwt)
                return ResponseEntity.ok("Request submitted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request  ,try again! ");
            }
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format");
        }
     
        
    }

    //UPDATE REQUEST
    @PostMapping("/{requestId}/accept")
    public ResponseEntity<String> acceptRequest(@PathVariable("requestId") Integer requestId) {
        requestService.acceptRequest(requestId);
        return ResponseEntity.ok("Request accepted successfully");
    }

    @PostMapping("/{requestId}/reject")
    public ResponseEntity<String> rejectRequest(@PathVariable("requestId") Integer requestId) {
        requestService.rejectRequest(requestId);
        return ResponseEntity.ok("Request rejected successfully");
    }

    @PutMapping("/updateRequest/{id}")
    public ResponseEntity<String> updateRequest(@RequestBody Map<String,String>request,@PathVariable Integer id) {

        String startstr= request.get("from");
        String endstr= request.get("to");
        Integer carid= Integer.parseInt(request.get("carid"));
        Integer userid= Integer.parseInt(request.get("userid"));
 
 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         Date start, end;
      try{

        
        Request r=new Request();
        start = dateFormat.parse(startstr);
        end = dateFormat.parse(endstr);
        r.setFrom(start); r.setTo(end); r.setVehicle(carid); r.setUser(userid); r.setStatus(RequestStatus.UNDER_REVIEW);

      
      // if (!requestService.checkRequestStatus(id)){
      //   return ResponseEntity.status(HttpStatus.NOT_FOUND).body("your request can not be updated after it had been accepted or no request is found");}//accepted request can not be edited

      boolean success=requestService.updateVehicleRequest(r,id);

      if (success){  return ResponseEntity.ok("Request Updated successfully"); }
               
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("could not update your request");
    }
      catch(Exception e){
        return ResponseEntity.badRequest().body("Invalid"+e);
      }
       
    }


    @GetMapping("/getrequesteByservid/{id}")
    public List<List<Request>> getrequesteByservid(@PathVariable Integer id) {
        return requestService.getVehiclesOfServiceProvider(id);
    }
    //DELETE REQUEST

    @DeleteMapping("/DeleteRequest/{id}")
    public ResponseEntity<String> deleteRequest(@PathVariable Integer id) {
         
      try{

      if (!requestService.requestWithThisId(id)){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No request with This id is found");}//accepted request can not be edited
      

      // if (!requestService.checkRequestStatus(id)){
      //   return ResponseEntity.status(HttpStatus.NOT_FOUND).body("your request can not be deleted after it had been accepted");}//accepted request can not be edited

      boolean success=requestService.deleteVehicleRequest(id);

      if (success){  return ResponseEntity.ok("Request deleted successfully"); }
               
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("could not delete your request");
    }
      catch(Exception e){
        return ResponseEntity.badRequest().body("Invalid "+e);
      }
    }
    
}


