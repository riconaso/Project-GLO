package com.corso.ProjectGLO.mapper;

import com.corso.ProjectGLO.dto.SubRedditDTO;
import com.corso.ProjectGLO.model.Post;
import com.corso.ProjectGLO.model.SubReddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring") //Quando si usano i mapper che servono per convertire i dati da dto a model e viceversa, si usa sempre questa annotazione
public interface SubRedditMapper {

    @Mapping(target = "numeroPost", expression = "java(mapPosts(subReddit.getPosts()))")
    SubRedditDTO mapSubRedditToDTO(SubReddit subReddit);

    default Integer mapPosts(List<Post> numeroPost){
        return numeroPost.size();
    }
    @Mapping(target = "posts", ignore = true)
    @InheritInverseConfiguration
    SubReddit mapDTOToSubReddit(SubRedditDTO subRedditDTO);



}
