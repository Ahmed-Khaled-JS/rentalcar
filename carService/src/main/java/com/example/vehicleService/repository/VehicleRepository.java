package com.example.vehicleService.repository;

//import com.example.vehicleService.dto.VehicleResponse;
import com.example.vehicleService.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Integer> {








}