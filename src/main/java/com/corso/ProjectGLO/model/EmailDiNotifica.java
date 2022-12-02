package com.corso.ProjectGLO.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EmailDiNotifica {

    private String soggetto;
    private String recipiente;
    private String corpo;

}
