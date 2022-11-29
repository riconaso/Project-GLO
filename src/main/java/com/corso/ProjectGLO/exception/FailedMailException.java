package com.corso.ProjectGLO.exception;

import org.springframework.mail.MailException;

public class FailedMailException extends RuntimeException {

    public FailedMailException(String message, MailException e) {
           super(message);
    }

}
