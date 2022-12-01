package com.corso.ProjectGLO.service;

import com.corso.ProjectGLO.dto.LoginRequest;
import com.corso.ProjectGLO.dto.RegisterRequest;
import com.corso.ProjectGLO.exception.ControllerNotFoundException;
import com.corso.ProjectGLO.model.EmailDiNotifica;
import com.corso.ProjectGLO.model.Utente;
import com.corso.ProjectGLO.model.VerificationToken;
import com.corso.ProjectGLO.repository.UtenteRepository;
import com.corso.ProjectGLO.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UtenteRepository utenteRepository;
    @Autowired
    VerificationTokenRepository verificationTokenRepository;
    @Autowired
    MailService mailService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Transactional
    public void signUp(RegisterRequest registerRequest) {
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
                "Grazie per esserti registrato a GLO, " + "Per attivare il tuo account clicca sul link" +
                        "\n" + "http://localhost:8080/api/auth/verification/"+ token)
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
    @Transactional
    public void verificaAccount(String token) {
        Optional<VerificationToken> optional = verificationTokenRepository.findByToken(token);
        optional.orElseThrow(() ->  new ControllerNotFoundException("token non trovato"));
        trovaUserEAbilita(optional.get());
    }
    @Transactional
    public void trovaUserEAbilita(VerificationToken verificationToken) {
        String username = verificationToken.getUtente().getUsername();
        Utente utente = utenteRepository.findByUsername(username).orElseThrow(() ->  new ControllerNotFoundException("utente non trovato"));
        utente.setEnabled(true);
        utenteRepository.save(utente);
    }

    public void login(LoginRequest loignRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loignRequest.getUsername(), loignRequest.getPassword()));

    }
    
}
