package com.pfe.projetpfe;


import com.pfe.projetpfe.entity.Admin;
import com.pfe.projetpfe.entity.Professeur;
import com.pfe.projetpfe.repository.AdminRepository;
import com.pfe.projetpfe.repository.ProfesseurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class ProjetPfeApplication {


    public static void main(String[] args) {
        SpringApplication.run(ProjetPfeApplication.class, args);
    }
}
