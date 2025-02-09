package com.pfe.projetpfe.service;

import com.pfe.projetpfe.Dto.ProfDto;
import com.pfe.projetpfe.entity.Professeur;
import com.pfe.projetpfe.repository.ProfesseurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProfServiceImp implements ProfService {

    ProfesseurRepository professeurRepository;

    @Override
    public ProfDto getProfByName(String name) {
       Optional<Professeur> professeur= professeurRepository.findByNom(name);
       if(!professeur.isPresent()){
           return null;
       }
       ProfDto profDto = new ProfDto();

       profDto.setId(professeur.get().getId());
       profDto.setNom(professeur.get().getNom());
       profDto.setPrenom(professeur.get().getPrenom());
       profDto.setEmail(professeur.get().getEmail());
       return profDto;
    }

    @Override
        public Professeur encryptPassword(Professeur user) {
            String pwd = user.getPassword();
            BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
            String hashPwd = bc.encode(pwd);
            user.setPassword(hashPwd);

            return user;
        }

    @Override
    public List<ProfDto> getAllProfs() {
        List<Professeur> professeurs = professeurRepository.findAll();
        List<ProfDto> profs = new ArrayList<>();
        for (Professeur prof : professeurs) {
            ProfDto profDto = new ProfDto();

            profDto.setId(prof.getId());
            profDto.setNom(prof.getNom());
            profDto.setPrenom(prof.getPrenom());
            profDto.setEmail(prof.getEmail());
            profs.add(profDto);
        }
        return profs;
    }

}
