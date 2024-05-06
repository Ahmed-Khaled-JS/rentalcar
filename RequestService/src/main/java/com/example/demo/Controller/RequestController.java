package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Request;
import com.example.demo.Model.Request.RequestStatus;
import com.example.demo.Model.User;
import com.example.demo.Model.Vehicle;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.VehicleRepo;
import com.example.demo.Service.RequestService;

import java.util.Map;
import java.util.Optional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;




@RestController
@RequestMapping("/request")
public class RequestController {

  //  @Autowired
  //   RequestValidatyService requestValidatyService;

    @Autowired
    RequestService requestService;

    @Autowired
    VehicleRepo vehicleRepo;

    @Autowired
    UserRepository userRepo;

    @PostMapping("/makeRequest")
    public ResponseEntity<String> makeRequest(@RequestBody Map<String,String>request) {

      //requestValidatyService.validateAndExtractUsername();

      
        //getting the token from the header
        // final String jwt ;
        // final String authHeader=request.getHeader("Authorization");
        // jwt=authHeader.substring(7);

       String startstr= request.get("from");
       String endstr= request.get("to");
       String model= request.get("model");



       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start, end;
        try {
            Request r=new Request();
            start = dateFormat.parse(startstr);
            end = dateFormat.parse(endstr);
            Optional<Vehicle> v = vehicleRepo.findBycarName(model);
            Optional<User> u = userRepo.findById(3);//mehataa id el consumer haggebo men el token wala eh?
            if (v.isPresent() && u.isPresent()) {
                Vehicle vv = v.get();
                User uu=u.get(); 
                Long daysBetween = ChronoUnit.DAYS.between(start.toInstant(), end.toInstant());

              Long totalPrice= daysBetween*(vv.getCarPricePerDay());
               r.setFrom(start); r.setTo(end); r.setVehicle(vv); r.setUser(uu); r.setStatus(RequestStatus.UNDER_REVIEW);
               r.setTotalPrice(totalPrice);
            }
            else {
                return ResponseEntity.badRequest().body("No vehicle with this model is Found");
               }
            
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

    @PutMapping("/updateRequest/{id}")
    public ResponseEntity<String> updateRequest(@RequestBody Map<String,String>request,@PathVariable Integer id) {
        
        String startstr= request.get("from");
        String endstr= request.get("to");
        String model= request.get("model");
 
 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         Date start, end;
      try{

        
        Request r=new Request();
        start = dateFormat.parse(startstr);
        end = dateFormat.parse(endstr);
        Optional<Vehicle> v = vehicleRepo.findBycarName(model);//byt2ked en el model ely howa talbo mawgood 3ndena
        Optional<User> u = userRepo.findById(1);//mehataa id el consumer haggebo men el token wala eh?
        if (v.isPresent() && u.isPresent()) {
            Vehicle   vv = v.get();
            User uu=u.get();
            Long daysBetween = ChronoUnit.DAYS.between(start.toInstant(), end.toInstant());
            Long totalPrice= daysBetween*(vv.getCarPricePerDay());
           r.setFrom(start); r.setTo(end); r.setVehicle(vv); r.setUser(uu); r.setStatus(RequestStatus.UNDER_REVIEW); r.setTotalPrice(totalPrice);
      }
      else {
       return ResponseEntity.badRequest().body("No vehicle with this model is Found");
      }
      
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


