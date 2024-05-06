package com.example.vehicleService.repository;

import com.example.vehicleService.dto.VehicleResponse;
// import com.example.vehicleService.models.Vehicle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Example;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
// import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
// import java.util.Optional;
// import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class VehicleRepositoryImp implements MyRepository {


    @Autowired
    private final EntityManager entityManager;


    @Override
    public List<VehicleResponse> searchVehicles(String query) {
        String jpqlQuery = "SELECT v FROM Vehicle v WHERE v.carName LIKE CONCAT('%', :query, '%')";
        TypedQuery<VehicleResponse> typedQuery = entityManager.createQuery(jpqlQuery, VehicleResponse.class);
        typedQuery.setParameter("query", query);
        return typedQuery.getResultList();
    }

    @Override
    public List<VehicleResponse> getVehiclesOfServiceProvider(Integer serviceProviderId){

        TypedQuery<VehicleResponse> query = entityManager.createQuery("SELECT v FROM Vehicle v WHERE v.serviceProviderId = :serviceProviderId",
                VehicleResponse.class);
        query.setParameter("serviceProviderId", serviceProviderId);
        List<VehicleResponse> resultList = query.getResultList();

        return resultList ;

    }

    @Override
    public VehicleResponse findVehicleByName(String carName) {
        String jpqlQuery = "SELECT v FROM Vehicle v WHERE v.carName = :carName";
        TypedQuery<VehicleResponse> query = entityManager.createQuery(jpqlQuery, VehicleResponse.class);
        query.setParameter("carName", carName);
        return query.getSingleResult();
    }



}