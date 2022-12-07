package com.corso.ProjectGLO.service;

import com.corso.ProjectGLO.dto.VoteDTO;
import com.corso.ProjectGLO.exception.PostNotFoundException;
import com.corso.ProjectGLO.exception.SpringRedditException;
import com.corso.ProjectGLO.model.Post;
import com.corso.ProjectGLO.model.Voto;
import com.corso.ProjectGLO.repository.PostRepository;
import com.corso.ProjectGLO.repository.VotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.corso.ProjectGLO.model.TipoVoto.UP_VOTE;
import static com.corso.ProjectGLO.model.TipoVoto.DOWN_VOTE;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VotoService {
    private final VotoRepository votoRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDTO voteDTO) {
        Post post = postRepository.findById(voteDTO.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDTO.getPostId()));
        Optional<Voto> voteByPostAndUser = votoRepository.findTopByPostAndUtenteOrderByIdVotoDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getTipovoto()
                        .equals(voteDTO.getTipoVoto())) {
            throw new SpringRedditException("You have already "
                    + voteDTO.getTipoVoto() + "'d for this post");
        }
        if (UP_VOTE.equals(voteDTO.getTipoVoto())) {
            post.setContatoreVoti(post.getContatoreVoti() + 1);
        } else {
            post.setContatoreVoti(post.getContatoreVoti() - 1);
        }
        votoRepository.save(mapToVote(voteDTO, post));
        postRepository.save(post);
    }

    private Voto mapToVote(VoteDTO voteDTO, Post post) {
        return Voto.builder()
                .Tipovoto(voteDTO.getTipoVoto())
                .post(post)
                .utente(authService.getCurrentUser())
                .build();
    }
}

