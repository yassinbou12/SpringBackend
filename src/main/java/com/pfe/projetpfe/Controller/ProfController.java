package com.pfe.projetpfe.Controller;


import com.pfe.projetpfe.Dto.*;
import com.pfe.projetpfe.repository.FiliereRepository;
import com.pfe.projetpfe.repository.ModuleRepositorie;
import com.pfe.projetpfe.repository.ProfesseurRepository;
import com.pfe.projetpfe.repository.ResourcesRepository;
import com.pfe.projetpfe.service.AdminService;
import com.pfe.projetpfe.service.ProfesseurService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import  com.pfe.projetpfe.entity.Module;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/professeur")
@PreAuthorize("hasRole('PROFESSEUR')")
public class ProfController {
    ProfesseurRepository professeurRepository;
    FiliereRepository filiereRepository;
    ModuleRepositorie moduleRepositorie;
    ResourcesRepository resourcesRepository;
    ProfesseurService professeurService;
    AdminService adminService;

    public ProfController(ProfesseurRepository professeurRepository, FiliereRepository filiereRepository, ModuleRepositorie moduleRepositorie, ResourcesRepository resourcesRepository, ProfesseurService professeurService,AdminService adminService) {
        this.professeurRepository = professeurRepository;
        this.filiereRepository = filiereRepository;
        this.moduleRepositorie = moduleRepositorie;
        this.resourcesRepository = resourcesRepository;
        this.professeurService = professeurService;
        this.adminService = adminService;
    }
    @GetMapping(path = "/GetProfesseur/{email}")
    public ResponseEntity<?> ChercheProfesseur(@PathVariable String email) throws Exception {
        ProfDto professeur =adminService.getProfByEmail(email);
        if (professeur==null) {
            throw new Exception("Professeur " +email+" n'exist pas");
        }
        return ResponseEntity.ok().body(professeur);
    }
    @GetMapping(path="/getAllFiliere")
    public ResponseEntity<?> getAllFiliere() {
        try {
            List<ReturnFiliereDto> filiereList = adminService.getAllFilieres();
            return ResponseEntity.ok().body(filiereList);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //Gestion des modules
    @PostMapping(path="/AddNewModule")
    public ResponseEntity<?> AddNewModule(@RequestBody AddModuleDto moduleAjouterDto){
        try {
            ReturnModuleDto returnModuleDto =professeurService.addModule(moduleAjouterDto);
            return ResponseEntity.ok(returnModuleDto);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
    public ResponseEntity<?> ModifyModule(@RequestBody ReturnModuleDto returnModuleDto){
        try {
            ReturnModuleDto returnModuleDto1 =professeurService.updateModule(returnModuleDto);
            return ResponseEntity.ok(returnModuleDto1);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping(path="/getAllModuleByProfId/{id}")
    public ResponseEntity<?> getAllModule(@PathVariable Long id) throws Exception{
        List<ReturnModuleDto> returnModuleDtos=professeurService.findModuleByProfesseurId(id);
        if(!returnModuleDtos.isEmpty()) {
            return ResponseEntity.ok(returnModuleDtos);
        }
        return ResponseEntity.notFound().build();
    }
    //Gestion des resources
    @PostMapping(path="/addResource" ,consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addResources(@ModelAttribute AddResourceDto ajouteResourceDto){
        try {
            ResourceReturnDto resourceReturnDto=professeurService.addResources(ajouteResourceDto);
            return ResponseEntity.ok(resourceReturnDto);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping(path="/updateResource",consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateResource(@ModelAttribute  UpdateResourceDto updateResourceDto){
        try {
            ResourceReturnDto resourceReturnDto1=professeurService.updateResources(updateResourceDto);
            return ResponseEntity.ok(resourceReturnDto1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping(path="/deleteResource/{id}")
    public ResponseEntity<?> deleteResource(@PathVariable Long id){
        try {
            resourcesRepository.deleteById(id);
            return ResponseEntity.ok().body("Resource deleted successfully");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("resource not found");
        }
    }
    @GetMapping(path="/getAllResources/{id}")
    public ResponseEntity<?> getAllResources(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(professeurService.getAllResourcesByProfId(id));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
