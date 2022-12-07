package com.corso.ProjectGLO.controller;

import com.corso.ProjectGLO.dto.SubRedditDTO;
import com.corso.ProjectGLO.exception.SubRedditException;
import com.corso.ProjectGLO.model.SubReddit;
import com.corso.ProjectGLO.service.SubRedditService;
import com.corso.ProjectGLO.service.SubRedditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subreddit")
public class SubRedditController {

    private final SubRedditService subRedditService;

    @PostMapping("/new")
    public ResponseEntity<SubRedditDTO>saveSubReddit(@RequestBody SubRedditDTO subRedditDTO){
      //  subRedditService.saveSubReddit(subRedditDTO);
       // return new ResponseEntity<>("account salavato con successo!", HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.CREATED).body(subRedditService.saveSubReddit(subRedditDTO));
    }


    @GetMapping("/all-subreddit")
    public ResponseEntity<List<SubRedditDTO>>getAllSubReddit(){
        List<SubRedditDTO> listaSubReddit = subRedditService.getAllSubReddit();
        return ResponseEntity.ok(listaSubReddit);// NON SERVE LA LOGICA CON GLI IF DATO CHE LO FA NEL SERVICE
     }

     @GetMapping("/subreddit/{id}")
    public ResponseEntity<SubRedditDTO>getOneById(@PathVariable long id){
         SubRedditDTO subRedditDTO = subRedditService.getOneById(id);
         return ResponseEntity.ok(subRedditDTO);


     }





}
