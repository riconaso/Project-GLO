package com.corso.ProjectGLO.repository;

import com.corso.ProjectGLO.model.Commento;
import com.corso.ProjectGLO.model.Post;
import com.corso.ProjectGLO.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentoRepository extends JpaRepository<Commento, Long> {

    List<Commento> findAllByPost(Post post);
    List<Commento> findByPost(Post post);
    List<Commento> findAllByUtente(Utente utente);

}