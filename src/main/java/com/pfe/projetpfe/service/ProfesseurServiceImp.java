package com.pfe.projetpfe.service;

import com.pfe.projetpfe.Dto.ModuleAjouterDto;
import com.pfe.projetpfe.Dto.ReturnFiliereDto;
import com.pfe.projetpfe.Dto.ReturnModuleDto;
import com.pfe.projetpfe.entity.Filiere;
import com.pfe.projetpfe.entity.Professeur;
import com.pfe.projetpfe.repository.FiliereRepository;
import com.pfe.projetpfe.repository.ModuleRepositorie;
import com.pfe.projetpfe.repository.ProfesseurRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pfe.projetpfe.entity.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfesseurServiceImp implements ProfesseurService {
    ModuleRepositorie moduleRepositorie;
    FiliereRepository filiereRepository;
    ProfesseurRepository professeurRepository;

    public ProfesseurServiceImp(ModuleRepositorie moduleRepositorie, FiliereRepository filiereRepository,ProfesseurRepository professeurRepository) {
        this.moduleRepositorie = moduleRepositorie;
        this.filiereRepository = filiereRepository;
        this.professeurRepository = professeurRepository;
    }

    @Override
    public List<ReturnModuleDto> findModuleByProfesseurId(Long professeurId) {
        List<Module> modules=moduleRepositorie.findByProfesseurId(professeurId);
        List<ReturnModuleDto> moduleListDto=new ArrayList<>();
        for (Module module : modules) {
            ReturnModuleDto returnModuleDto=new ReturnModuleDto();
            returnModuleDto.setId(module.getModuleId());
            returnModuleDto.setName(module.getModuleName());
            returnModuleDto.setSemestre(module.getSemestre());
            returnModuleDto.setFiliereName(module.getFiliere().getNomFiliere());
            moduleListDto.add(returnModuleDto);
        }
        return moduleListDto;
    }

    @Override
    public ReturnModuleDto addModule(ModuleAjouterDto moduleAjouterDto) {
        Optional<Filiere> filiere=filiereRepository.findByNomFiliere(moduleAjouterDto.getFiliereName());

        if(!filiere.isPresent()) {
            throw new RuntimeException("Filiere non trouvable");
        }
        Optional<Professeur> professeur=professeurRepository.findById(moduleAjouterDto.getIdProfesseur());
        Module module=new Module();
        module.setModuleName(moduleAjouterDto.getName());
        module.setSemestre(moduleAjouterDto.getSemestre());
        module.setFiliere(filiere.get());
        module.setProfesseur(professeur.get());

        Module savedModule=moduleRepositorie.save(module);

        ReturnModuleDto returnModuleDto=new ReturnModuleDto(savedModule.getModuleId(),savedModule.getModuleName(),savedModule.getSemestre(),savedModule.getFiliere().getNomFiliere());
        return returnModuleDto;
    }

    @Override
    public ReturnModuleDto updateModule(ReturnModuleDto returnModuleDto) {
        Optional<Module> module =moduleRepositorie.findById(returnModuleDto.getId());
        if(!module.isPresent()) {
            new RuntimeException("Module non trouvable");
        }

        Optional<Filiere> filiere=filiereRepository.findByNomFiliere(returnModuleDto.getFiliereName());
        if(!filiere.isPresent()) {
            new RuntimeException("Filiere non trouvable");
        }

        Module updateMudole=module.get();
        updateMudole.setModuleName(returnModuleDto.getName());
        updateMudole.setSemestre(returnModuleDto.getSemestre());
        updateMudole.setFiliere(filiere.get());

        updateMudole =moduleRepositorie.save(updateMudole);

        ReturnModuleDto moduleDto=new ReturnModuleDto(updateMudole.getModuleId(),updateMudole.getModuleName(),updateMudole.getSemestre(),updateMudole.getFiliere().getNomFiliere());

        return moduleDto;
    }

}
