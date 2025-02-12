package com.pfe.projetpfe.Configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/**").permitAll()  // Permet toutes les requêtes
                                .anyRequest().authenticated()  // Si tu veux que d'autres requêtes soient authentifiées
                )
                // Désactive le formulaire de connexion
                .formLogin(login -> login
                        .loginPage("/login")
                        .permitAll())
                .httpBasic(httpSecurityHttpBasicConfigurer ->
                        httpSecurityHttpBasicConfigurer.disable())
                .csrf(httpSecurityCsrfConfigurer ->httpSecurityCsrfConfigurer.disable());
        return http.build();  // Retourne le filtre de sécurité configuré
    }
}

