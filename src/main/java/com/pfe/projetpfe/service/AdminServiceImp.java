package com.pfe.projetpfe.service;

import com.pfe.projetpfe.Dto.AdminDto;
import com.pfe.projetpfe.Dto.ProfDto;
import com.pfe.projetpfe.Dto.RegistrDto;
import com.pfe.projetpfe.entity.Admin;
import com.pfe.projetpfe.entity.Professeur;
import com.pfe.projetpfe.repository.AdminRepository;
import com.pfe.projetpfe.repository.ProfesseurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class AdminServiceImp implements AdminService {

    ProfesseurRepository professeurRepository;
    AdminRepository adminRepository;

    public AdminServiceImp(ProfesseurRepository professeurRepository, AdminRepository adminRepository) {
        this.professeurRepository = professeurRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public ProfDto getProfByName(String name) {
       Optional<Professeur> professeur=professeurRepository.findByNom(name);
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
    public ProfDto getProfByEmail(String email) {
        Optional<Professeur> professeur= professeurRepository.findByEmail(email);
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
    public AdminDto getAdminById(Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        if(!admin.isPresent()){
            return null;
        }
        AdminDto adminDto = new AdminDto();
        adminDto.setNom(admin.get().getNom());
        adminDto.setPrenom(admin.get().getPrenom());
        adminDto.setEmail(admin.get().getEmail());
        return adminDto;
    }

    @Override
        public String encryptPassword(String password) {
            BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
            String hashPwd = bc.encode(password);

            return hashPwd;
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
