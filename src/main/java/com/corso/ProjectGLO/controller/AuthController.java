package com.corso.ProjectGLO.controller;

import com.corso.ProjectGLO.dto.RegisterRequest;
import com.corso.ProjectGLO.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping ("/api/auth")
public class AuthController {

   private final AuthService authService;

     @PostMapping("/signUp")
    public ResponseEntity<String>signUp(@RequestBody RegisterRequest registerRequest){
        authService.signUp(registerRequest);
        return new ResponseEntity<>("Account creato con succeso!", HttpStatus.OK);
    }
}
