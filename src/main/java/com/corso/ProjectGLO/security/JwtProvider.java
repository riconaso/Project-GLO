package com.corso.ProjectGLO.security;


import com.corso.ProjectGLO.model.Utente;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtProvider {


   private  JwtEncoder jwtEncoder;
   @Value("${jwt.expiration.time}")
   private long jwtExpirationInMillis;

    public String generateToken(Authentication authentication){
        User utente = (User) authentication.getPrincipal();
        return generateTokenWithUsername(utente.getUsername());
    }

    public String generateTokenWithUsername(String username){
        JwtClaimsSet claims=JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(jwtExpirationInMillis))
                .subject(username)
                .claim("scope", "ROLL_USER")
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Long getJwtExpirationInMillis(){
        return jwtExpirationInMillis;
    }


}
