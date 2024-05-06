package com.example.vehicleService.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//import org.hibernate.annotations.ColumnDefault;
import org.springframework.stereotype.Component;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class VehicleRequest {

    @Size(min = 3, max = 20, message = "car name must be between 3 and 20 characters.")
    @NotNull(message = "car name is required")
    private String carName ;


  /*  @NotNull(message = "car image is required")
    private String carCoverImage ;*/


    @Size(min = 3, max = 3)
    @Pattern(regexp = "\\d+", message = "License plate must contain exactly 3 digits")
    @NotNull(message = "car license is required")
    private String carLicense ;



    @NotNull(message = "car price is required")
    private  String carPricePerDay ;



    @NotNull(message = "car address is required")
    private String carAddress ;

    @NotNull(message = "no of passengers is required")
    private String noOfPassengers ;


    @NotNull(message = "car description is required")
    private String carDesc ;



    private Boolean isAvaliable;



    private Integer serviceProviderId ;




}
