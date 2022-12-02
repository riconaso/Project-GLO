package com.corso.ProjectGLO.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", updatable = false, nullable = false)
    private long postId;

    @Column(name = "post_name")
    @NotBlank(message = "POST_NAME non può essere vuoto.")
    private String postName;

    @Column
    @Nullable
    private String url;

    @Lob // Un'annotazione Lob specifica che il campo deve essere reso persistente come oggetto di grandi dimensioni.
    @Nullable
    private String descrizione;

    @Column(name = "contatore_voti")
    private int contatoreVoti = 0;

    @Column(name = "data_creazione")
    private Instant dataCreazione;

    @ManyToOne
    @JoinColumn(name = "Utente_FK", referencedColumnName = "id_user")
    private Utente utente;
    @ManyToOne
    @JoinColumn(name = "Subreddit_FK", referencedColumnName = "id")
    private SubReddit subreddit;

}