package com.pfe.projetpfe.service;

import com.pfe.projetpfe.entity.Admin;
import com.pfe.projetpfe.entity.Professeur;
import com.pfe.projetpfe.repository.AdminRepository;
import com.pfe.projetpfe.repository.ProfesseurRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    public CustomUserDetailsService(ProfesseurRepository profRepository, AdminRepository adminRepository) {
        this.profRepository = profRepository;
        this.adminRepository = adminRepository;
    }

    private final ProfesseurRepository profRepository;
    private final  AdminRepository adminRepository;

    //L'impl de UserDetailsService (utilise les Repos pour communiquer avec DB)

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Professeur> prof = profRepository.findByEmail(email);
        if (prof.isPresent()) {
            return new User(
                    prof.get().getEmail(),
                    prof.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(prof.get().getRole().name()))
            );
        }

        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) {
            return new User(
                    admin.get().getEmail(),
                    admin.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(admin.get().getRole().name()))
            );
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}