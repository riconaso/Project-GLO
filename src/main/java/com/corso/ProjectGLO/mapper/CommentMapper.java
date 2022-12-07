package com.corso.ProjectGLO.mapper;

import com.corso.ProjectGLO.dto.CommentoDTO;
import com.corso.ProjectGLO.model.Commento;
import com.corso.ProjectGLO.model.Post;
import com.corso.ProjectGLO.model.Utente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target= "id", ignore = true)
    @Mapping(target = "testo", source = "commentoDTO.testo")
    @Mapping(target = "creationDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "utente", source = "utente")
    Commento map(CommentoDTO commentoDTO, Post post, Utente utente);

    @Mapping(target = "postId", expression = "java(commento.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(commento.getUtente().getUsername())")
    CommentoDTO mapToDto(Commento commento);
}
