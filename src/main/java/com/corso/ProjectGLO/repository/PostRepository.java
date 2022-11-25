package com.corso.ProjectGLO.repository;


import com.corso.ProjectGLO.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(Utente utente);
}
