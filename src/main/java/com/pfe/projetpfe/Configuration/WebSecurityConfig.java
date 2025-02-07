package com.pfe.projetpfe.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;




@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/home").permitAll()  // Adjust the paths as needed
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")              // Custom login page
                        .loginProcessingUrl("/perform_login")  // URL to process login
                        .permitAll())
                .logout(logout -> logout
                        .permitAll());

        return http.build();
    }
}

