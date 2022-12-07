package com.corso.ProjectGLO.service;

import com.corso.ProjectGLO.model.Utente;
import com.corso.ProjectGLO.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Utente> optionalUserDetails = utenteRepository.findByUsername(username);
        Utente user = optionalUserDetails.orElseThrow(() ->
                new UsernameNotFoundException("ERROR: User not found." + username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities("USER"));
    }

    private Collection<?extends GrantedAuthority> getAuthorities (String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}


