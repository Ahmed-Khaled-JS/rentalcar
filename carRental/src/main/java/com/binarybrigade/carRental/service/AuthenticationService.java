package com.binarybrigade.carRental.service;

import com.binarybrigade.carRental.config.JwtService;
import com.binarybrigade.carRental.models.AuthenticationRequest;
import com.binarybrigade.carRental.models.RegsisterRequest;
import com.binarybrigade.carRental.models.User;
import com.binarybrigade.carRental.repository.UserRepository;
import com.binarybrigade.carRental.response.AuthenticationResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.naming.ldap.PagedResultsControl;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository ;

    private final PasswordEncoder passwordEncoder ;

    private final JwtService jwtService ;
    //private final User userModel ;





    @Autowired
    private AuthenticationManager authenticationManager ;



    public AuthenticationResponse register(RegsisterRequest request) {

        if (repository.findByEmail(request.getEmail()).isEmpty()) {

            User.Role role;

            if(request.getRole() == User.Role.SERVICE_PROVIDER){

                role = User.Role.SERVICE_PROVIDER;
            }
            else {
                role = User.Role.CONSUMER;
            }



            var user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();

            repository.save(user);

            var jwtToken = jwtService.createToken(user);
            return AuthenticationResponse.builder()
                    .message("registration success")
                    .token(jwtToken)
                    .build();
        }

        return AuthenticationResponse.builder()
                .message("This email already exists")
                .token("no token")
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
     Optional<User>  userData=  repository.findByEmail(request.getEmail());

        if (userData.isPresent()) {

            User userInfo = userData.get();

            String hashedPassword=userInfo.getPassword() ;

            if(passwordEncoder.matches(request.getPassword(),hashedPassword)){

                authenticationManager.authenticate(

                        new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())


                );

                var user=repository.findByEmail(request.getEmail()).orElseThrow();

                var jwtToken=jwtService.createToken(user);
                return AuthenticationResponse.builder()
                        .message("sign in  sucess")
                        .token(jwtToken)
                        .build();

            }

            else {

                return AuthenticationResponse.builder()
                        .message("password is incorrect to this email")
                        .token("no token")
                        .build();

            }





        }





        else{
              return AuthenticationResponse.builder()
                    .message("This email not exists before")
                    .token("no token")
                    .build();

        }

        }


    public User getUserById(Integer userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));
    }
}
