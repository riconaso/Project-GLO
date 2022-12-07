package com.corso.ProjectGLO.repository;

import com.corso.ProjectGLO.model.Post;
import com.corso.ProjectGLO.model.Utente;
import com.corso.ProjectGLO.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    Optional<Voto> findTopByPostAndUtenteOrderByIdVotoDesc(Post post, Utente currentUser);
}


