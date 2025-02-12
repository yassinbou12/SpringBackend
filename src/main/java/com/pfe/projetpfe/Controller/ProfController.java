package com.pfe.projetpfe.Controller;


import com.pfe.projetpfe.Dto.ModuleAjouterDto;
import com.pfe.projetpfe.Dto.ReturnModuleDto;
import com.pfe.projetpfe.entity.Filiere;
import com.pfe.projetpfe.entity.Professeur;
import com.pfe.projetpfe.repository.FiliereRepository;
import com.pfe.projetpfe.repository.ModuleRepositorie;
import com.pfe.projetpfe.repository.ProfesseurRepository;
import com.pfe.projetpfe.repository.ResourcesRepository;
import com.pfe.projetpfe.service.ProfesseurService;
import jakarta.el.PropertyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import  com.pfe.projetpfe.entity.Module;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/professeur")

public class ProfController {
    ProfesseurRepository professeurRepository;
    FiliereRepository filiereRepository;
    ModuleRepositorie moduleRepositorie;
    ResourcesRepository resourcesRepository;
    ProfesseurService professeurService;

    public ProfController(ProfesseurRepository professeurRepository, FiliereRepository filiereRepository, ModuleRepositorie moduleRepositorie, ResourcesRepository resourcesRepository, ProfesseurService professeurService) {
        this.professeurRepository = professeurRepository;
        this.filiereRepository = filiereRepository;
        this.moduleRepositorie = moduleRepositorie;
        this.resourcesRepository = resourcesRepository;
        this.professeurService = professeurService;
    }

    @PostMapping(path="/AddNewModule")
    public ResponseEntity<?> AddNewModule(@RequestBody ModuleAjouterDto moduleAjouterDto) throws Exception{
        Optional<Module> moduleRepository =moduleRepositorie.findByModuleName(moduleAjouterDto.getName());
        if(moduleRepository.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Module already exists");
        }
        Optional<Filiere> filiere=filiereRepository.findByNomFiliere(moduleAjouterDto.getFiliereName());

        if(!filiere.isPresent()) {
            return ResponseEntity.badRequest().body("Filiere n'existe pas");
        }
        Optional<Professeur> professeur=professeurRepository.findById(moduleAjouterDto.getIdProfesseur());
        Module module=new Module();
        module.setModuleName(moduleAjouterDto.getName());
        module.setSemestre(moduleAjouterDto.getSemestre());
        module.setFiliere(filiere.get());
        module.setProfesseur(professeur.get());

        Module savedModule=moduleRepositorie.save(module);

        ReturnModuleDto returnModuleDto=new ReturnModuleDto(savedModule.getModuleId(),savedModule.getModuleName(),savedModule.getSemestre(),savedModule.getFiliere().getNomFiliere());
        return ResponseEntity.ok(returnModuleDto);
    }

    @DeleteMapping(path="/DeleteModule/{id}")
    public ResponseEntity<?> DeleteModule(@PathVariable Long id) throws Exception{
        Optional<Module> module =moduleRepositorie.findById(id);
        if(!module.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        moduleRepositorie.delete(module.get());
        return ResponseEntity.ok().body("Module deleted successfully");
    }
    @PutMapping(path="/ModifyModule")
    public ResponseEntity<?> ModifyModule(@RequestBody ReturnModuleDto returnModuleDto) throws Exception{
        Optional<Module> module =moduleRepositorie.findById(returnModuleDto.getId());
        if(!module.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Filiere> filiere=filiereRepository.findByNomFiliere(returnModuleDto.getFiliereName());
        if(!filiere.isPresent()) {
            return ResponseEntity.badRequest().body("Filiere n'existe pas");
        }

        Module updateMudole=module.get();
        updateMudole.setModuleName(returnModuleDto.getName());
        updateMudole.setSemestre(returnModuleDto.getSemestre());
        updateMudole.setFiliere(filiere.get());
        moduleRepositorie.save(updateMudole);

        ReturnModuleDto moduleDto=new ReturnModuleDto(updateMudole.getModuleId(),updateMudole.getModuleName(),updateMudole.getSemestre(),updateMudole.getFiliere().getNomFiliere());

        return ResponseEntity.ok().body(moduleDto);
    }
    @GetMapping(path="/getAllModule/{id}")
    public ResponseEntity<?> getAllModule(@PathVariable Long id) throws Exception{
        List<ReturnModuleDto> returnModuleDtos=professeurService.findByProfesseurId(id);
        if(!returnModuleDtos.isEmpty()) {
            return ResponseEntity.ok(returnModuleDtos);
        }
        return ResponseEntity.notFound().build();
    }
}
