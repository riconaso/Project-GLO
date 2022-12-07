package com.corso.ProjectGLO.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class SubReddit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;
    @NotBlank
    private String nome;
    @NotBlank
    private String descrizione;
    private Instant dataCreazione;

    @OneToMany
    @JoinColumn(name= "post_id")
    private List<Post> posts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name= "Utente_Fk")
    private Utente utente;


}
