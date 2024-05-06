package com.binarybrigade.carRental.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {


    private final static String SECRET_KEY="3D5ZzmhVBxeLMGl5sLIDVGQ3IWlrcdVAf0nsGKwLE0OrZC5MJoucKZ/rGwSN/eLR" ;


    public String extractUsername(String token){

        return extractClaim(token,Claims::getSubject) ; // he say the subject of the token is the userName

    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsReslover){

        final Claims claims=extractAllClaims(token);

        return claimsReslover.apply(claims);

    }

    public String createToken(UserDetails userDetails){ //create token without extract claims

        return createToken(new HashMap<>(),userDetails);

    }



    public String createToken(Map<String,Object> extractClaims, UserDetails userDetails){

        extractClaims.put("name", userDetails.getUsername());
        extractClaims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new));

        return Jwts
                .builder() //this for build token
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) // this to know which time the token created
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))// the token expired after 24 hours
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)// key of token and which algorthism
                .compact();// this for returning the token
    }


    public boolean isTokenValid(String token,UserDetails userDetails){

        final String username=extractUsername(token) ;

        return (username.equals(userDetails.getUsername())) && (!isTokenExpired(token));

    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());

    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }


    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().
                parseClaimsJws(token).getBody();


    }


    public Key getSignInKey(){

        byte[] keBytes= Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keBytes);

    }





}
