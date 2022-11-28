package com.corso.ProjectGLO.controller.advice;

import com.corso.ProjectGLO.controller.exception.ControllerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice // è un catch globale che gestisce tutte l'eccezioni
@ResponseBody // che possono verificarsi all'interno di un'applicazione SpringMVC
public class GlobalExceptionHandler {

    @ExceptionHandler(ControllerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String notFoundHandler(ControllerNotFoundException ex) {
        return ex.getMessage();
    }
}
