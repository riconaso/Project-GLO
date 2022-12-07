package com.corso.ProjectGLO.service;

import com.corso.ProjectGLO.dto.AuthenticationResponse;
import com.corso.ProjectGLO.dto.LoginRequest;
import com.corso.ProjectGLO.dto.RefreshTokenRequest;
import com.corso.ProjectGLO.dto.RegisterRequest;
import com.corso.ProjectGLO.exception.ControllerNotFoundException;
import com.corso.ProjectGLO.model.EmailDiNotifica;
import com.corso.ProjectGLO.model.Utente;
import com.corso.ProjectGLO.model.VerificationToken;
import com.corso.ProjectGLO.repository.UtenteRepository;
import com.corso.ProjectGLO.repository.VerificationTokenRepository;
import com.corso.ProjectGLO.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
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
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    RefreshTokenService refreshTokenService;

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

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    @Transactional(readOnly = true)
    public Utente getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return utenteRepository.findByUsername(principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
