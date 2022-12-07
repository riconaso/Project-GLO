package com.corso.ProjectGLO.dto;

import com.corso.ProjectGLO.model.TipoVoto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {
    private TipoVoto tipoVoto;
    private Long postId;
}