package com.corso.ProjectGLO.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Post_Id", updatable = false, nullable = false)
    private long post_id;

    @Column(name = "nome_post")
    private String nome_post;

    @Column(name = "url", nullable = false)
    private String url;

    @Lob // Un'annotazione Lob specifica che il campo deve essere reso persistente come oggetto di grandi dimensioni.
    @Column(name = "descrizione", nullable = false)
    private String descrizione;

    private int contatore_voti;

  //  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
 //   private Set<Commenti> comments = new HashSet<>();





}
