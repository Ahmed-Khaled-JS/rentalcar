package com.binarybrigade.carRental.response;

import lombok.Builder;

@Builder
public class AuthenticationResponse {


    private String message ;

    private String token ;

    public AuthenticationResponse(){

    }

    public AuthenticationResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}


