package com.corso.ProjectGLO.service;

import com.corso.ProjectGLO.dto.CommentoDTO;
import com.corso.ProjectGLO.exception.PostNotFoundException;
import com.corso.ProjectGLO.exception.SpringRedditException;
import com.corso.ProjectGLO.mapper.CommentMapper;
import com.corso.ProjectGLO.model.Commento;
import com.corso.ProjectGLO.model.EmailDiNotifica;
import com.corso.ProjectGLO.model.Post;
import com.corso.ProjectGLO.model.Utente;
import com.corso.ProjectGLO.repository.*;
import com.corso.ProjectGLO.repository.PostRepository;
import com.corso.ProjectGLO.repository.UtenteRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentoService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UtenteRepository utenteRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentoRepository commentoRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentoDTO commentoDTO){
        Post post = postRepository.findById(commentoDTO.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentoDTO.getPostId().toString()));
        Commento commento = commentMapper.map(commentoDTO, post, authService.getCurrentUser());
        commentoRepository.save(commento);

        String message = mailContentBuilder.build(post.getUtente().getUsername()
                + " postato un commento al post." + POST_URL);
        sendCommentNotification(message, post.getUtente());
    }
    private void sendCommentNotification(String message, Utente utente) {
        mailService.sendMail(new EmailDiNotifica(utente.getUsername()
                + " Commentato il tuo post", utente.getEmail(), message));
    }

    public List<CommentoDTO> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentoRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentoDTO> getAllCommentsForUser(String userName) {
        Utente user = utenteRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentoRepository.findAllByUtente(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

    public boolean containsSwearWords(String comment) {
        if (comment.contains("shit")) {
            throw new SpringRedditException("Comments contains unacceptable language");
        }
        return false;
    }

}
