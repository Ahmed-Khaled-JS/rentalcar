package com.example.vehicleService.dto;

// import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.Pattern;
// import jakarta.validation.constraints.Size;
import com.example.vehicleService.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {

    private Integer carId ;

    private String carName ;


    private String carCoverImage ;



    private String carLicense ;



    private  String carPricePerDay ;




    private String carAddress ;


    private String noOfPassengers ;



    private String carDesc ;


    private Boolean isAvaliable ;



    private Integer serviceProviderId ;


    private User user ;


}
