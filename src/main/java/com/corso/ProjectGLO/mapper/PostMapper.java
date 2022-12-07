package com.corso.ProjectGLO.mapper;

import com.corso.ProjectGLO.dto.PostRequest;
import com.corso.ProjectGLO.dto.PostResponse;
import com.corso.ProjectGLO.model.*;
import com.corso.ProjectGLO.repository.CommentoRepository;
import com.corso.ProjectGLO.repository.VotoRepository;
import com.corso.ProjectGLO.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    private AuthService authService;
    @Autowired
    private VotoRepository votoRepository;
    @Autowired
    private CommentoRepository commentoRepository;

    @Mapping(target ="dataCreazione", expression = "java(java.time.Instant.now())")
    @Mapping(target="description", source = "postRequest.description")
    @Mapping(target="subReddit", source = "subReddit")
    @Mapping(target="voteCount",constant = "0")
    @Mapping(target= "utente", source = "utente")
    public abstract Post mapDTOToModel(PostRequest postRequest, SubReddit subReddit, Utente utente);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subRedditName", source = "subReddit.nome")
    @Mapping(target = "username", source ="utente.username")
   // @Mapping(target = "commentCount", expression = "java(commentCount(post))")
   // @Mapping(target = "duration", expression = "java(getDuration(post))")
   // @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
   // @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    public abstract PostResponse mapToDTO(Post post);

    Integer commentCount(Post post) {
        return commentoRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getDataCreazione().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UP_VOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, TipoVoto tipoVoto) {
        if (authService.isLoggedIn()) {
            Optional<Voto> voteForPostByUser =
                    votoRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                            authService.getCurrentUser());
            return voteForPostByUser.filter(voto -> voto.getTipoVoto().equals(tipoVoto))
                    .isPresent();
        }
        return false;
    }







}