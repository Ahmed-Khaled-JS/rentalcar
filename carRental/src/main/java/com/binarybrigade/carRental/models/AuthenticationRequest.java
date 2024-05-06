package com.binarybrigade.carRental.models;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationRequest {

    @Email(message = "please enter a valid email")
    @NotNull(message = "email is required")
    private String email ;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long and should contain " +
                    "at least one digit, one lowercase letter, one uppercase letter, " +
                    "one special character, and no whitespace.")
    @NotNull(message = "password is required")
    private String password ;


}
