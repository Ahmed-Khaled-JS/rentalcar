package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Model.Request;

import java.util.Date;
import java.util.List;


public interface RequestRepo extends JpaRepository<Request,Integer>{
    List<Request> findAllByVehicleidAndRequestStatus(Integer vehicleId, Request.RequestStatus status);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Request e WHERE (?1 <= e.to AND ?2 >= e.from) AND e.vehicleid = ?3")
    Boolean existsByDateRangeAndVehicleId(Date newRequestStartDate, Date newRequestEndDate, Integer vehicleId);
     //vehicle can not be rented at overlapping time
}
