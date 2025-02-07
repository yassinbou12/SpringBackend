package com.pfe.projetpfe.service;

import com.pfe.projetpfe.Dto.UserDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ProfService {
    public UserDto encryptPassword(UserDto user) {
        String pwd = user.getPassword();
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String hashPwd = bc.encode(pwd);
        user.setPassword(hashPwd);

        return user;
    }
}
