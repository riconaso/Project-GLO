package com.corso.ProjectGLO.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.query.criteria.internal.predicate.IsEmptyPredicate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "comments")
public class Commento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;
    @NotEmpty
    private String testo;
    private Instant dataCreazione;

    @ManyToOne
    @JoinColumn(name = "Post_Fk")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "Utente_Fk")
    private Utente utente;

}
