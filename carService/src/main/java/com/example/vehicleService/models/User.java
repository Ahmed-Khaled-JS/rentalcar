package com.example.vehicleService.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User  {

    private Integer id ;

    private String name ;

    private String email;

    private String phone ;

    private String password ;

    public String Role;

}
