package com.example.vehicleService.controllers;

import com.example.vehicleService.dto.VehicleRequest;
// import com.example.vehicleService.dto.VehicleResponse;
// import com.example.vehicleService.models.Vehicle;
import com.example.vehicleService.responses.VehicleApiResponse;
import com.example.vehicleService.services.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {


    private final VehicleService vehicleService ;
   // private final VehicleRequest vehicleRequest ;


    @PostMapping("/add")
public ResponseEntity<Object> addVehicle(@ModelAttribute @Valid VehicleRequest vehicleRequest,
                                         @RequestParam("carCoverImage")MultipartFile file) throws IOException {

        return vehicleService.addVehicle(vehicleRequest,file) ;

    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<Object> deleteVehicle(@PathVariable Integer id){
        return vehicleService.removeCar(id) ;

    }

    @PutMapping("update/{id}")
    public ResponseEntity<Object> updateVehicle(@ModelAttribute @Valid VehicleRequest vehicleRequest,
                                                @PathVariable Integer id,@RequestParam("carCoverImage")MultipartFile file)
    throws IOException{

        return vehicleService.updateVehicle(vehicleRequest,id,file);


    }


    @GetMapping("/ownerVehicles/{serviceProviderId}")
    public VehicleApiResponse getVehiclesByServiceProviderId(@PathVariable Integer serviceProviderId) {
        return vehicleService.getVehiclesOfServiceProvider(serviceProviderId);
    }

    @GetMapping("/allVehicles")
    public VehicleApiResponse retrieveVehicles(){

        return vehicleService.getVehicles() ;

    }
    @PutMapping("/{carId}/available")
    public ResponseEntity<String> updateAvailabilityToTrue(@PathVariable("carId") Integer carId) {
        vehicleService.updateAvailabilityToTrue(carId);
        return ResponseEntity.ok("Vehicle availability updated to true");
    }

    @PutMapping("/{carId}/unavailable")
    public ResponseEntity<String> updateAvailabilityToFalse(@PathVariable("carId") Integer carId) {
        vehicleService.updateAvailabilityToFalse(carId);
        return ResponseEntity.ok("Vehicle availability updated to false");
    }

    @GetMapping("/search")
    public VehicleApiResponse searchVehicles(@RequestParam("searchName") String query){
        return vehicleService.searchVechicles(query);
    }




    @GetMapping("getuserdatilswithcarid")
    public VehicleApiResponse getVehiclesByCarId(){
        return vehicleService.getAllVehicles();
    }


    @GetMapping("getCarById/{id}")
    public Boolean getVehicleById(@PathVariable Integer id){
        return vehicleService.isgetVehicleById(id);
    }
    @ExceptionHandler(BindException.class)

    public ResponseEntity<HashMap<String, List<String>>> handleBindException(BindException ex){

        List<String>errors=ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        HashMap<String ,List<String>>errMap=new HashMap<>();
        errMap.put("errors",errors);
        return new ResponseEntity<>(errMap, HttpStatus.BAD_REQUEST);


    }





}
