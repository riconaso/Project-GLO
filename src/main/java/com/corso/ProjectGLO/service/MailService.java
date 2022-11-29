package com.corso.ProjectGLO.service;

import com.corso.ProjectGLO.exception.FailedMailException;
import com.corso.ProjectGLO.model.EmailDiNotifica;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j //Ci permette di utilizzare i log senza crearli
public class MailService {
    //Iniezione senza Autowired tramite costruttore (AllArgsConstructor)
    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    //
    void sendMail(EmailDiNotifica emailDiNotifica){
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("provaGlo@mail.com");
            mimeMessageHelper.setTo(emailDiNotifica.getRecipiente());
            mimeMessageHelper.setSubject(emailDiNotifica.getSoggetto());
            mimeMessageHelper.setText(emailDiNotifica.getCorpo());
        };

        try {
            mailSender.send(mimeMessagePreparator);
            log.info("Email inviata con successo!");
        } catch(MailException e){
            log.error("Si è verificata un'eccezione durante l'invio dell'email", e);
            throw new FailedMailException("Si è verificata un'eccezione durante l'invio dell'email a" + emailDiNotifica.getRecipiente(), e);
        }

    }
}
