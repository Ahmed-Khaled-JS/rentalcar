package com.binarybrigade.carRental.models;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegsisterRequest {

    @Size(min = 3, max = 20, message = "Name must be between 2 and 20 characters.")
    @NotNull(message = "username is required")
    private String name ;


    @Email(message = "please enter a valid email")
    @NotNull(message = "email is required")
    private String email ;


    @Pattern(regexp = "^01[0-2][0-9]{8}$",
            message = "Please enter a valid Egyptian phone number.")

    @NotNull(message = "phone number is required")
    private String phone ;



    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long and should contain " +
                    "at least one digit, one lowercase letter, one uppercase letter, " +
                    "one special character, and no whitespace.")
    @NotNull(message = "password is required")
    private String password ;

    @Enumerated(EnumType.STRING) // this annoation to tell spring that role is an enum
    private User.Role role ;


}
