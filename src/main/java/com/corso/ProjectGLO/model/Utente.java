package com.corso.ProjectGLO.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "utenti")
public class Utente {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_user", updatable = false, nullable = false)
    @Id
    private long userID;

    @Column(name= "usernames")
    @NotBlank(message = "L'username non può essere vuoto!")
    private String username;

    @Column(name= "passwords")
    @NotBlank(message = "La password non può essere vuota!")
    private String password;

    @Email
    @Column(name="emails")
    private String email;

    private Instant created;

}
