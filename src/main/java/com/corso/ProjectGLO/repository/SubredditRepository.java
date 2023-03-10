package com.corso.ProjectGLO.repository;

import com.corso.ProjectGLO.model.SubReddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubredditRepository extends JpaRepository<SubReddit, Long> {

    Optional<SubReddit> findByNome(String subredditName);
}