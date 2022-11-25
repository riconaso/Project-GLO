package com.corso.ProjectGLO.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static javax.persistence.FetchType.LAZY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", updatable = false, nullable = false)
    private long postId;

    @Column(name = "post_name")
    @NotBlank(message = "POST_NAME non può essere vuoto.")
    private String postName;

    @Column(nullable = false)
    private String url;

    @Lob // Un'annotazione Lob specifica che il campo deve essere reso persistente come oggetto di grandi dimensioni.
    @Column(nullable = false)
    private String descrizione;

    @Column(name = "contatore_voti", nullable = false)
    private int contatoreVoti;

    // @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    // @JoinColumn
    // private List<Commento> comments;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "Utente_FK")
    private Utente utente;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "Subreddit_FK")
    private Subreddit subreddit;

}