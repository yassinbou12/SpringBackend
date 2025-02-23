package com.pfe.projetpfe.Controller;

import com.pfe.projetpfe.Dto.*;
import com.pfe.projetpfe.Dto.AddFiliereDto;
import com.pfe.projetpfe.entity.*;
import com.pfe.projetpfe.repository.AdminRepository;
import com.pfe.projetpfe.repository.FiliereRepository;
import com.pfe.projetpfe.repository.ProfesseurRepository;
import com.pfe.projetpfe.service.AdminServiceImp;
import com.pfe.projetpfe.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class adminController {



    private AdminService adminService;
    private ProfesseurRepository professeurRepository;
    private AdminRepository adminRepository;
    private FiliereRepository filiereRepository;

    public adminController(AdminService adminService, ProfesseurRepository professeurRepository, AdminRepository adminRepository, FiliereRepository filiereRepository) {;
        this.adminService = adminService;
        this.professeurRepository = professeurRepository;
        this.adminRepository = adminRepository;
        this.filiereRepository = filiereRepository;
    }
    //Gestion des professeurs
    @PostMapping(path = "/AddNewProfesseur")
    public ResponseEntity<?> AddNewProfesseur(@RequestBody RegistrDto professeurRegistrDto) {
        ProfDto profDto= adminService.addNewProf(professeurRegistrDto);
        try{
            return ResponseEntity.ok().body(profDto);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(path = "/UpdateProfesseur")
    public ResponseEntity<?> updateProfesseur(@RequestBody Professeur professeur) throws Exception {

        ProfDto profDto= adminService.updateProf(professeur);
        try {
            return ResponseEntity.ok().body(profDto);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "DeleteProfesseur/{id}")
    public ResponseEntity<?> DeleteProfesseur(@PathVariable Long id) throws Exception {
        Optional professeur = professeurRepository.findById(id);
        if (!professeur.isPresent()) {
            throw new Exception("Professeur  with ID: " + id+" not found ");
        }
        professeurRepository.deleteById(id);
        return ResponseEntity.ok().body("Professeur deleted");
    }

    @GetMapping(path = "/ListProfesseurs")
    public ResponseEntity<?> ListProfesseurs() {
        List<ProfDto> professeursDto = adminService.getAllProfs();
        if (professeursDto.isEmpty()) {
            // Retourner une réponse avec un code 400 si la liste est vide
            return ResponseEntity.badRequest().body("Aucun professeur trouvé");
        }
        return ResponseEntity.ok(professeursDto);
    }

    @GetMapping(path = "/GetProfesseur/{email}")
    public ResponseEntity<?> ChercheProfesseur(@PathVariable String email) throws Exception {
        ProfDto professeur =adminService.getProfByEmail(email);
        if (professeur==null) {
            throw new Exception("Professeur " +email+" n'exist pas");
        }
        return ResponseEntity.ok().body(professeur);
    }

    //Gestion de profil admin
    //not working
    @PutMapping(path = "/ModifierProfil")
    public ResponseEntity<?> ModifierProfil(@RequestBody Admin admin) throws Exception {
        Optional<Admin> adminRepo= adminRepository.findById(admin.getId());
        if (!adminRepo.isPresent()) {
            return ResponseEntity.badRequest().body("Admin n'existe pas ");
        }
        Admin adminUpdate = adminRepository.save(admin);

        AdminDto adminDto =adminService.getAdminById(adminUpdate.getId());
        return ResponseEntity.ok().body(adminDto);
    }
    //Gestion des filieres
    @PostMapping(path="/AddNewFiliere")
    public ResponseEntity<?> addNewFiliere(@RequestBody AddFiliereDto filiereDto) throws Exception {
        ReturnFiliereDto returnFiliereDto = adminService.addNewFiliere(filiereDto);
        try {
            return ResponseEntity.ok().body(returnFiliereDto);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path="/RemoveFiliere/{id}")
    public ResponseEntity<?> removeFiliere(@PathVariable Long id) throws Exception {
        Optional<Filiere> filiereOptional = filiereRepository.findById(id);
        if (!filiereOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Filiere n'existe pas ");
        }
        filiereRepository.deleteById(id);
        return ResponseEntity.ok().body("Filiere deleted");
    }
    @PutMapping(path="/ModifyFiliere")
    public ResponseEntity<?> modifyFiliere(@RequestBody ReturnFiliereDto filiere) throws Exception {
        ReturnFiliereDto returnFiliereDto = adminService.updateFiliere(filiere);
        try {
            return ResponseEntity.ok().body(returnFiliereDto);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping(path="/getAllFiliere")
    public ResponseEntity<?> getAllFiliere() throws Exception {
        List<ReturnFiliereDto> filiereList = adminService.getAllFilieres();
        try {
            return ResponseEntity.ok().body(filiereList);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping(path = "/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto){
        try {
            adminService.updatePassword(updatePasswordDto);
            return ResponseEntity.ok().body("Password updated successfully");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
