package com.example.vehicleService.repository;

import com.example.vehicleService.dto.VehicleResponse;
//import com.example.vehicleService.models.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyRepository {

    public List<VehicleResponse> searchVehicles(String query);

    public List<VehicleResponse> getVehiclesOfServiceProvider(Integer serviceProviderId);

    public VehicleResponse findVehicleByName(String carName) ;




}
