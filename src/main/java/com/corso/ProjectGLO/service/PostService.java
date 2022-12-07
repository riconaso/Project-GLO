package com.corso.ProjectGLO.service;

import com.corso.ProjectGLO.dto.PostRequest;
import com.corso.ProjectGLO.dto.PostResponse;
import com.corso.ProjectGLO.exception.PostNotFoundException;
import com.corso.ProjectGLO.exception.SubRedditException;
import com.corso.ProjectGLO.mapper.PostMapper;
import com.corso.ProjectGLO.model.Post;
import com.corso.ProjectGLO.model.SubReddit;
import com.corso.ProjectGLO.model.Utente;
import com.corso.ProjectGLO.repository.PostRepository;
import com.corso.ProjectGLO.repository.SubredditRepository;
import com.corso.ProjectGLO.repository.UtenteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j //Fa il logger da solo
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UtenteRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        SubReddit subreddit = subredditRepository.findByName(postRequest.getSubRedditName())
                .orElseThrow(() -> new SubRedditException(postRequest.getSubRedditName()));
        postRepository.save(postMapper.mapDTOToModel(postRequest, subreddit, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDTO(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDTO)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        SubReddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubRedditException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDTO).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        Utente utente = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(utente)
                .stream()
                .map(postMapper::mapToDTO)
                .collect(toList());
    }
}
