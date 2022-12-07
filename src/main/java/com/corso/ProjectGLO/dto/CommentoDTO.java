package com.corso.ProjectGLO.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentoDTO {
    private long id;
    private Long postId;
    private Instant creationDate;
    private String testo;
    private String username;

}
