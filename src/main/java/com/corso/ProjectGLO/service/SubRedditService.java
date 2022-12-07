package com.corso.ProjectGLO.service;

import com.corso.ProjectGLO.dto.SubRedditDTO;
import com.corso.ProjectGLO.exception.SubRedditException;
import com.corso.ProjectGLO.mapper.SubRedditMapper;
import com.corso.ProjectGLO.model.SubReddit;
import com.corso.ProjectGLO.repository.SubredditRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SubRedditService {
    @Autowired
    private SubredditRepository subredditRepository;
    @Autowired
    private SubRedditMapper subRedditMapper;


    @Transactional
    public SubRedditDTO saveSubReddit(SubRedditDTO subRedditDTO){
        SubReddit subReddit = subRedditMapper.mapDTOToSubReddit(subRedditDTO);
        subredditRepository.save(subReddit);
        subRedditDTO.setId(subReddit.getId());
        return subRedditDTO;

    }
    @Transactional
    public List<SubRedditDTO> getAllSubReddit(){
        List<SubReddit> listaSubReddit = subredditRepository.findAll();
        return listaSubReddit.stream() //LO STREAM ITERA LA MAPPA SENZA CHE FACCIAMO NOI A MANO I CICLI
                .map(subRedditMapper::mapSubRedditToDTO)
                .collect(Collectors.toList());

    }

    public SubRedditDTO getOneById(long id){

        SubReddit subreddit = subredditRepository.findById(id).orElseThrow(()-> new SubRedditException
                ("Sub Reddit con questo id non trovato: "+ id) );
        return subRedditMapper.mapSubRedditToDTO(subreddit);

    }

}
