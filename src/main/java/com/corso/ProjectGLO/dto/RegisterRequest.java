package com.corso.ProjectGLO.dto;

import com.corso.ProjectGLO.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Email
    private String email;
    private String username;
    private String password;

    @RestController
    @RequestMapping("/api/auth")
    public static class AuthController {

        @Autowired
        private AuthService authService;

        @PostMapping("/signUp")
        public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest) {
            authService.signUp(registerRequest);
            return new ResponseEntity<>("Account creato con successo!", HttpStatus.OK);
        }
    }
}
