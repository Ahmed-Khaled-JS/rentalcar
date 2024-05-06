package com.example.demo.Model;


//import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;  L
//import org.springframework.security.core.authority.SimpleGrantedAuthority;  L
//import org.springframework.security.core.userdetails.UserDetails;  L

//import java.util.Collection;  L
//import java.util.List; L

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")

public class User { //shelt extends

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id ;


    @Column(name = "username")
    @Size(min = 3, max = 20, message = "Name must be between 2 and 50 characters.")
    @NotNull(message = "username is required")
    private String name ;


    @Column(name = "email")
    @Email(message = "please enter a valid email")
    @NotNull(message = "email is required")
    private String email ;

    @Column(name = "phone")
    @Pattern(regexp = "^01[0-2][0-9]{8}$",
            message = "Please enter a valid Egyptian phone number.")

    @NotNull(message = "phone number is required")
    private String phone ;

    @Column(name = "password")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long and should contain " +
                    "at least one digit, one lowercase letter, one uppercase letter, " +
                    "one special character, and no whitespace.")
    @NotNull(message = "password is required")
    private String password ;

    public enum Role {


        CONSUMER,
        SERVICE_PROVIDER,
        ADMIN
    }

    @Column(name = "role")
    @Enumerated(EnumType.STRING) // this annoation to tell spring that role is an enum
    private Role role ;


    ///LOBNA UPDATES



    
//    @OneToMany(cascade = CascadeType.ALL)
//     @JoinColumn(name="Puser_id",referencedColumnName = "user_id")
//     private List<Vehicle> vehicle;

 ///ehtagt azawed constructor msh byakhod el id 3ala asas eno auto increment


//     @OneToMany(cascade = CascadeType.ALL)
//     @JoinColumn(name="Ruser_id",referencedColumnName = "user_id")
//     private List<Request> request;

//end of Lobna's updates

}
