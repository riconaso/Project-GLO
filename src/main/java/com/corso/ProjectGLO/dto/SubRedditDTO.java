package com.corso.ProjectGLO.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubRedditDTO {
    private Long id;
    private String nome;
    private String descrizione;
    private Integer numeroPost;
}

