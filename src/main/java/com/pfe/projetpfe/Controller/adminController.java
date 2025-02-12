package com.pfe.projetpfe.Controller;

import com.pfe.projetpfe.Dto.*;
import com.pfe.projetpfe.Dto.AddFiliereDto;
import com.pfe.projetpfe.entity.*;
import com.pfe.projetpfe.repository.AdminRepository;
import com.pfe.projetpfe.repository.FiliereRepository;
import com.pfe.projetpfe.repository.ProfesseurRepository;
import com.pfe.projetpfe.repository.RoleRepository;
import com.pfe.projetpfe.service.AdminServiceImp;
import com.pfe.projetpfe.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/admin")
public class adminController {

    private final RoleRepository roleRepository;
    private AdminService AdminService;
    private ProfesseurRepository professeurRepository;
    private AdminRepository adminRepository;
    private FiliereRepository filiereRepository;

    public adminController(RoleRepository roleRepository, AdminService adminService, ProfesseurRepository professeurRepository, AdminRepository adminRepository, FiliereRepository filiereRepository) {
        this.roleRepository = roleRepository;
        AdminService = adminService;
        this.professeurRepository = professeurRepository;
        this.adminRepository = adminRepository;
        this.filiereRepository = filiereRepository;
    }

    @PostMapping(path = "/addNewProfesseur")
    public ResponseEntity<?> AddNewProfesseur(@RequestBody RegistrDto professeurRegistrDto) {
        Optional<Professeur> professeurOptional=professeurRepository.findByEmail(professeurRegistrDto.getEmail());
        if( professeurOptional.isPresent() ) {
            return ResponseEntity.badRequest().body(professeurRegistrDto.getEmail()+"deja exist");
        }
        //recuperer les info de register
        Professeur professeur = new Professeur();
        professeur.setNom(professeurRegistrDto.getNom());
        professeur.setPrenom(professeurRegistrDto.getPrenom());
        professeur.setEmail(professeurRegistrDto.getEmail());
        //hashPassword
        professeur.setPassword(AdminService.encryptPassword(professeurRegistrDto.getPassword()));
        //ajouter le role
        AppRole role= roleRepository.findByroleName(TypeRole.PROFESSEUR);
        professeur.setRole(role);
        //first password true
        professeur.setFirstPassword(true);
        //save
        professeurRepository.save(professeur);

        ProfDto profDto =AdminService.getProfByEmail(professeur.getEmail());


        return ResponseEntity.ok().body(profDto);

    }
    @PutMapping(path = "/UpdateProfesseur")
    public ResponseEntity<?> updateProfesseur(@RequestBody Professeur professeur) throws Exception {
        Optional<Professeur> existingProfesseur = professeurRepository.findById(professeur.getId());
        if (!existingProfesseur.isPresent()) {
            throw new Exception("Professeur with ID: " + professeur.getId()+" not found ");
        }

        // Update logic
        Professeur updatedProfesseur = existingProfesseur.get();
        updatedProfesseur.setNom(professeur.getNom());
        updatedProfesseur.setPrenom(professeur.getPrenom());
        updatedProfesseur.setEmail(professeur.getEmail());

        Professeur prof = professeurRepository.save(updatedProfesseur);

        ProfDto profDto =AdminService.getProfByEmail(prof.getEmail());
        
        return ResponseEntity.ok().body(profDto );
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
        List<ProfDto> professeursDto = AdminService.getAllProfs();

        if (professeursDto.isEmpty()) {
            // Retourner une réponse avec un code 400 si la liste est vide
            return ResponseEntity.badRequest().body("Aucun professeur trouvé");
        }

        return ResponseEntity.ok(professeursDto);
    }

    @GetMapping(path = "/GetProfesseur/{nom}")
    public ResponseEntity<?> ChercheProfesseur(@PathVariable String nom) throws Exception {
        ProfDto professeur =AdminService.getProfByName(nom);
        if (professeur==null) {
            throw new Exception("Professeur " +nom+" n'exist pas");
        }
        return ResponseEntity.ok().body(professeur);
    }
    
    @PutMapping(path = "/ModifierProfil")
    public ResponseEntity<?> ModifierProfil(@RequestBody Admin admin) throws Exception {
        Optional<Admin> adminRepo= adminRepository.findById(admin.getId());
        if (!adminRepo.isPresent()) {
            return ResponseEntity.badRequest().body("Admin n'existe pas ");
        }
        Admin adminUpdate = adminRepository.save(admin);

        AdminDto adminDto =AdminService.getAdminById(adminUpdate.getId());
        return ResponseEntity.ok().body(adminDto);
    }

    @PostMapping(path="/AddNewFiliere")
    public ResponseEntity<?> addNewFiliere(@RequestBody AddFiliereDto filiereDto) throws Exception {
        Optional<Filiere> filiereOptional = filiereRepository.findByNomFiliere(filiereDto.getNom());
        if (filiereOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Filiere existe ");
        }
        Filiere filiere =new Filiere();
        filiere.setNomFiliere(filiereDto.getNom());
        filiere= filiereRepository.save(filiere);
        ReturnFiliereDto returnFiliereDto = new ReturnFiliereDto(filiere.getIdFiliere(), filiereDto.getNom());
        return  ResponseEntity.ok().body(returnFiliereDto);
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
        Optional<Filiere> filiereOptional = filiereRepository.findById(filiere.getId());
        if (!filiereOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Filiere n'existe pas ");
        }
        Filiere filiere1=(Filiere) filiereOptional.get();
        filiere1.setNomFiliere(filiere.getNom());
        filiere1= filiereRepository.save(filiere1);
        ReturnFiliereDto returnFiliereDto=new ReturnFiliereDto(filiere1.getIdFiliere(), filiere1.getNomFiliere());

        return ResponseEntity.ok().body(returnFiliereDto);

    }
    @GetMapping(path="/getAllFiliere")
    public ResponseEntity<?> getAllFiliere() throws Exception {
        List<Filiere> filiereRepository1 = filiereRepository.findAll();
        if (filiereRepository1.isEmpty()) {
            return ResponseEntity.badRequest().body("la liste des Filiere est vide ");
        }
        List<ReturnFiliereDto> returnFiliereDto=new ArrayList<>();
        for (Filiere filiere : filiereRepository1) {
            ReturnFiliereDto returnFiliereDto1=new ReturnFiliereDto(filiere.getIdFiliere(), filiere.getNomFiliere());
            returnFiliereDto.add(returnFiliereDto1);
        }
        return ResponseEntity.ok().body(returnFiliereDto);
    }


}
