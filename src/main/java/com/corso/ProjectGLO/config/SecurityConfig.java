package com.corso.ProjectGLO.config;

import com.corso.ProjectGLO.model.Utente;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.TemplateEngine;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
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

    @Bean
    TemplateEngine templateEngine() {
        return new TemplateEngine();
    }
}
