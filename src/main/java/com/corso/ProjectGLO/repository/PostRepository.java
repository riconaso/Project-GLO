package com.corso.ProjectGLO.repository;

import com.corso.ProjectGLO.model.Post;
import com.corso.ProjectGLO.model.SubReddit;
import com.corso.ProjectGLO.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
