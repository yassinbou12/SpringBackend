package com.pfe.projetpfe.service;

import com.pfe.projetpfe.entity.Professeur;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ProfServiceImp implements com.projetpfe.service.ProfService {
    @Override
        public Professeur encryptPassword(Professeur user) {
            String pwd = user.getPassword();
            BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
            String hashPwd = bc.encode(pwd);
            user.setPassword(hashPwd);

            return user;
        }
}
