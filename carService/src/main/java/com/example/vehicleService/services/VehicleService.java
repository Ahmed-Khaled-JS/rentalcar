package com.example.vehicleService.services;


import com.example.vehicleService.dto.VehicleRequest;
import com.example.vehicleService.dto.VehicleResponse;
import com.example.vehicleService.models.User;
import com.example.vehicleService.models.Vehicle;
import com.example.vehicleService.repository.MyRepository;
import com.example.vehicleService.repository.VehicleRepository;
import com.example.vehicleService.responses.ApiResponse;
import com.example.vehicleService.responses.VehicleApiResponse;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {

    @Autowired
    private final VehicleRepository vehicleRepository ;

    @Autowired
    private final MyRepository myRepository ;

    private final RestTemplate restTemplate;


   // private final EntityManager entityManager ;


    private static final String USER_SERVICE_BASE_URL = "http://localhost:8095"; // Change to actual URL of User Service

    public VehicleApiResponse getAllVehicles() {
        // Make a request to get all vehicles from the Vehicle Service
        // For demonstration purposes, let's assume you have a method to get all vehicles
        VehicleApiResponse vehicles = getVehicles();


        // For each vehicle, fetch user details from the User Service
        for (VehicleResponse vehicle : vehicles.getResult()) {
            Integer userId = vehicle.getServiceProviderId();
            ResponseEntity<User> responseEntity = restTemplate.getForEntity(USER_SERVICE_BASE_URL + "/api/v1/auth/getuserbyid/{userId}", User.class, userId);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                User user = responseEntity.getBody();
                vehicle.setUser(user);
            }
        }
        String message="done from here" ;
        vehicles.setMessage(message);
        return vehicles;
    }

public static String uploadDirectory=System.getProperty("user.dir")+"/carService/src/main/webapp/images";

    public ResponseEntity<Object> addVehicle(VehicleRequest vehicleRequest,
              MultipartFile file)throws IOException {

        String orginalFileName=file.getOriginalFilename();
        Path fileNameAndPath= Paths.get(uploadDirectory,orginalFileName);
        Files.write(fileNameAndPath,file.getBytes());


        Vehicle vehicle=Vehicle.builder()
                .carName(vehicleRequest.getCarName())
                .carDesc(vehicleRequest.getCarDesc())
                .carCoverImage(orginalFileName)
                .carPricePerDay(vehicleRequest.getCarPricePerDay())
                .carLicense(vehicleRequest.getCarLicense())
                .noOfPassengers(vehicleRequest.getNoOfPassengers())
                .carAddress(vehicleRequest.getCarAddress())
                .isAvaliable(true)
                .serviceProviderId(vehicleRequest.getServiceProviderId())
                .build();


        vehicleRepository.save(vehicle);

        String message = "Car added successfully";

        ApiResponse apiResponse=new ApiResponse(message);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);


    }
    public void updateAvailabilityToTrue(Integer carId) {
        Vehicle vehicle = vehicleRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + carId));
        vehicle.setIsAvaliable(true);
        vehicleRepository.save(vehicle);
    }
    public void updateAvailabilityToFalse(Integer carId) {
        Vehicle vehicle = vehicleRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + carId));
        vehicle.setIsAvaliable(false);
        vehicleRepository.save(vehicle);
    }

    public VehicleApiResponse getVehiclesOfServiceProvider(Integer serviceProviderId){

      //  List<Vehicle> vehicles=vehicleRepository.findByServiceProviderId(serviceProviderId);



        List<VehicleResponse> resultList = myRepository.getVehiclesOfServiceProvider(serviceProviderId);

        String message="vehicles of service provider get sucess" ;

        return new VehicleApiResponse(message,resultList);


    }



    /*..............*/


    public VehicleApiResponse getVehicles(){

        List<Vehicle> vehicles=vehicleRepository.findAll();

        List<VehicleResponse> result=vehicles.stream().map(this::mapToAllVehicleResponse) .collect(Collectors.toList());

        String message="get vehicles sucess" ;

        return new VehicleApiResponse(message,result);



    }

    private VehicleResponse mapToAllVehicleResponse(Vehicle vehicle) {

        return VehicleResponse.builder()
                .carId(vehicle.getCarTd())
                .carName(vehicle.getCarName())
                .carDesc(vehicle.getCarDesc())
                .carCoverImage(vehicle.getCarCoverImage())
                .carPricePerDay(vehicle.getCarPricePerDay())
                .carLicense(vehicle.getCarLicense())
                .noOfPassengers(vehicle.getNoOfPassengers())
                .carAddress(vehicle.getCarAddress())
                .serviceProviderId(vehicle.getServiceProviderId())
                .isAvaliable(vehicle.getIsAvaliable())
                .build();


    }


    /*.........update Vehicle........*/


    public ResponseEntity<Object> updateVehicle(VehicleRequest vehicleRequest, Integer id,MultipartFile file)
    throws IOException{

        String orginalFileName=file.getOriginalFilename();
        Path fileNameAndPath= Paths.get(uploadDirectory,orginalFileName);
        Files.write(fileNameAndPath,file.getBytes());




       Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);


        Vehicle vehicle = optionalVehicle.get();

        vehicle.setCarName(vehicleRequest.getCarName());
        vehicle.setCarDesc(vehicleRequest.getCarDesc());
        vehicle.setCarAddress(vehicleRequest.getCarAddress());
        vehicle.setCarLicense(vehicleRequest.getCarLicense());
        vehicle.setCarCoverImage(orginalFileName);
        vehicle.setCarPricePerDay(vehicleRequest.getCarPricePerDay());
        vehicle.setNoOfPassengers(vehicleRequest.getNoOfPassengers());

      vehicle.setIsAvaliable(vehicleRequest.getIsAvaliable());


        vehicleRepository.save(vehicle);

        String message = "Car updated successfully";

        ApiResponse apiResponse=new ApiResponse(message);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    /*.......delete..........*/

    public ResponseEntity<Object> removeCar (Integer id) {
        Vehicle vehicle = vehicleRepository.findById(id).get();
        vehicleRepository.delete(vehicle);

        String message = "Car deleted successfully";

        ApiResponse apiResponse=new ApiResponse(message);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

    /*.......search..........*/
    public VehicleApiResponse searchVechicles(String query){
        List<VehicleResponse> result =myRepository.searchVehicles(query) ;
        String message="get vehicles sucess" ;

        return new VehicleApiResponse(message,result);
    }


    public Boolean isgetVehicleById(Integer id) {
        Vehicle vehicleResponsetiny = vehicleRepository.findById(id).get();
        return vehicleResponsetiny.getIsAvaliable();
    }
}
