package com.example.vehicleService.responses;

import com.example.vehicleService.dto.VehicleResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class VehicleApiResponse {

    @JsonProperty("message")
    private String message;


    @JsonProperty("result")
    private List<VehicleResponse> result;


    public VehicleApiResponse(String message, List<VehicleResponse> result) {
        this.message = message;
        this.result = result;
    }
}
