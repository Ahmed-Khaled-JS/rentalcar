package com.binarybrigade.carRental.controller;

import com.binarybrigade.carRental.models.AuthenticationRequest;
import com.binarybrigade.carRental.models.RegsisterRequest;
import com.binarybrigade.carRental.models.User;
import com.binarybrigade.carRental.response.AuthenticationResponse;
import com.binarybrigade.carRental.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5501")
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    final AuthenticationService authenticationService ;

    @PostMapping("/regsister")
    public ResponseEntity<AuthenticationResponse> regsister(@RequestBody @Valid RegsisterRequest request){


return ResponseEntity.ok(authenticationService.register(request));


    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request){


return ResponseEntity.ok(authenticationService.authenticate(request));



    }

    @GetMapping("/getuserbyid/{id}")
    public User getUserById(@PathVariable int id){
        return authenticationService.getUserById(id);
    }


    @ExceptionHandler(BindException.class)

    public ResponseEntity<HashMap<String, List<String>>> handleBindException(BindException ex){

        List<String>errors=ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        HashMap<String ,List<String>>errMap=new HashMap<>();
        errMap.put("errors",errors);
        return new ResponseEntity<>(errMap, HttpStatus.BAD_REQUEST);


    }





}
