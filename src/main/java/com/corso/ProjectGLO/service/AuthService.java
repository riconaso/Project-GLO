package com.corso.ProjectGLO.service;

import com.corso.ProjectGLO.dto.RegisterRequest;
import com.corso.ProjectGLO.model.EmailDiNotifica;
import com.corso.ProjectGLO.model.Utente;
import com.corso.ProjectGLO.model.VerificationToken;
import com.corso.ProjectGLO.repository.UtenteRepository;
import com.corso.ProjectGLO.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;
    UtenteRepository utenteRepository;
    VerificationTokenRepository verificationTokenRepository;
    MailService mailService;

    @Transactional
    public void Signup(RegisterRequest registerRequest) {
        Utente utente = new Utente();
        utente.setUsername(registerRequest.getUsername());
        utente.setEmail(registerRequest.getEmail());
        utente.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        utente.setCreated(Instant.now());
        utente.setEnabled(false);
        utenteRepository.save(utente);
        String token = generateVerificationToken(utente);
        mailService.sendMail(new EmailDiNotifica(
                "Attiva il tuo account", utente.getEmail(),
                "Grazie per esserti registrato a GLO, " + token)
        );
    }


    private String generateVerificationToken(Utente utente) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUtente(utente);

        verificationTokenRepository.save(verificationToken);
        return token;
        
    }


}
