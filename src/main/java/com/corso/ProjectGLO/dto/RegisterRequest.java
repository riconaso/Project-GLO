package com.corso.ProjectGLO.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Email
    private String email;
    private String username;
    private String password;

}
