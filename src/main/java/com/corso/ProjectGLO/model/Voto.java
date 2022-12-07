package com.corso.ProjectGLO.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table (name="votes")
public class Voto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long idVoto;
    private TipoVoto Tipovoto;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "postId", referencedColumnName = "post_Id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id_user")
    private Utente utente;
}
