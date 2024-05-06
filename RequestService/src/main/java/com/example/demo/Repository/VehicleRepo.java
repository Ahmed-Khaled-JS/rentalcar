package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.Model.Vehicle;

public interface VehicleRepo  extends CrudRepository<Vehicle,Integer>{

    Optional<Vehicle> findBycarName(String carName);

}
