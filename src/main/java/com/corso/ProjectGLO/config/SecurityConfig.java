package com.corso.ProjectGLO.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public void Configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated();
        // csrf utilizza i cookie per l'autenticazione
        // antMatchers autorizzi le richieste dell'url passato come parametro: da questo pattern, tutte le richieste dopo auth/ vanno bene
    }

    @Bean
    PasswordEncoder passwordEncoder() { // per criptare la password
        return new BCryptPasswordEncoder();
    }

}
