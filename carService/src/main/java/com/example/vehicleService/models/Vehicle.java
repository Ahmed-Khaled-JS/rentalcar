package com.example.vehicleService.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class Vehicle {

    @Column(name = "car_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carTd;


    @Column(name = "car_name")
    private String carName;

    @Column(name = "car_coverImage")
    private String carCoverImage;

    @Column(name = "carLicense")
    private String carLicense;


    @Column(name = "car_price")
    private String carPricePerDay;


    @Column(name = "car_address")
    private String carAddress;

    @Column(name = "no_of_passengers")
    private String noOfPassengers;


    @Column(name = "car_Desc")
    private String carDesc;

    @Column(name = "isAvaliable")
    private Boolean isAvaliable ;


    @Column(name = "serviceProvider_id")
    private Integer serviceProviderId;


}
