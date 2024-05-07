package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {

    private Integer carTd ;

    private String carName ;


    private String carCoverImage ;



    private String carLicense ;



    private  String carPricePerDay ;




    private String carAddress ;


    private String noOfPassengers ;

    private String carDesc ;

    private Boolean isAvaliable ;

    private Integer serviceProviderId ;




}
