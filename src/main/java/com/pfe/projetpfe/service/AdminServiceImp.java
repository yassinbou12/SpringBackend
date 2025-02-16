package com.pfe.projetpfe.service;

import com.pfe.projetpfe.Dto.*;
import com.pfe.projetpfe.entity.*;
import com.pfe.projetpfe.repository.AdminRepository;
import com.pfe.projetpfe.repository.FiliereRepository;
import com.pfe.projetpfe.repository.ProfesseurRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class AdminServiceImp implements AdminService {

    ProfesseurRepository professeurRepository;
    AdminRepository adminRepository;
    FiliereRepository filiereRepository;

    public AdminServiceImp(ProfesseurRepository professeurRepository, AdminRepository adminRepository,FiliereRepository filiereRepository) {
        this.professeurRepository = professeurRepository;
        this.adminRepository = adminRepository;
        this.filiereRepository = filiereRepository;
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
    public ProfDto addNewProf(RegistrDto professeurRegistrDto) {
        Optional<Professeur> professeurOptional=professeurRepository.findByEmail(professeurRegistrDto.getEmail());
        if( professeurOptional.isPresent() ) {
             throw new RuntimeException(professeurOptional.get().getEmail()+" déje exist");
        }
        //recuperer les info de register
        Professeur professeur = new Professeur();
        professeur.setNom(professeurRegistrDto.getNom());
        professeur.setPrenom(professeurRegistrDto.getPrenom());
        professeur.setEmail(professeurRegistrDto.getEmail());
        //hashPassword
        professeur.setPassword(encryptPassword(professeurRegistrDto.getPassword()));

        //save
        professeurRepository.save(professeur);

        return getProfByEmail(professeur.getEmail());
    }

    @Override
    public ProfDto updateProf(Professeur professeur) {
        Optional<Professeur> existingProfesseur = professeurRepository.findById(professeur.getId());
        if (!existingProfesseur.isPresent()) {
            throw new RuntimeException("Professeur with ID: " + professeur.getId()+" not found ");
        }

        // Update logic
        Professeur updatedProfesseur = existingProfesseur.get();
        updatedProfesseur.setNom(professeur.getNom());
        updatedProfesseur.setPrenom(professeur.getPrenom());
        updatedProfesseur.setEmail(professeur.getEmail());

        Professeur prof = professeurRepository.save(updatedProfesseur);

        return getProfByEmail(prof.getEmail());
    }

    @Override
    public ReturnFiliereDto addNewFiliere(AddFiliereDto addFiliereDto) {
        Optional<Filiere> filiereOptional = filiereRepository.findByNomFiliere(addFiliereDto.getNom());
        if (filiereOptional.isPresent()) {
            throw new RuntimeException("Filiere : " + addFiliereDto.getNom()+" déja exist");
        }
        Filiere filiere =new Filiere();
        filiere.setNomFiliere(addFiliereDto.getNom());
        filiere= filiereRepository.save(filiere);
        ReturnFiliereDto returnFiliereDto = new ReturnFiliereDto(filiere.getIdFiliere(), addFiliereDto.getNom());
        return  returnFiliereDto;
    }

    @Override
    public ReturnFiliereDto updateFiliere(ReturnFiliereDto filiere) {
        Optional<Filiere> filiereOptional = filiereRepository.findById(filiere.getId());
        if (!filiereOptional.isPresent()) {
            throw  new RuntimeException("Filiere n'existe pas");
        }
        Filiere filiere1=(Filiere) filiereOptional.get();
        filiere1.setNomFiliere(filiere.getNom());
        filiere1= filiereRepository.save(filiere1);
        ReturnFiliereDto returnFiliereDto=new ReturnFiliereDto(filiere1.getIdFiliere(), filiere1.getNomFiliere());
        return  returnFiliereDto;
    }

    @Override
    public List<ReturnFiliereDto> getAllFilieres() {
        List<Filiere> filiereRepository1 = filiereRepository.findAll();
        if (filiereRepository1.isEmpty()) {
            throw  new RuntimeException("la liste des Filiere est vide ");
        }
        List<ReturnFiliereDto> returnFiliereDtos=new ArrayList<>();
        for (Filiere filiere : filiereRepository1) {
            ReturnFiliereDto returnFiliereDto1=new ReturnFiliereDto(filiere.getIdFiliere(), filiere.getNomFiliere());
            returnFiliereDtos.add(returnFiliereDto1);
        }
        return returnFiliereDtos;
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
