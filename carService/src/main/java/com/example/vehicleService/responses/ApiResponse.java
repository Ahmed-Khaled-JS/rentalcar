package com.example.vehicleService.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse {
    @JsonProperty("message")
    private String message;



    public ApiResponse(String message) {
        this.message = message;
    }



}
