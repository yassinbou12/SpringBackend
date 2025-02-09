package com.pfe.projetpfe.Conrtoller;


import com.pfe.projetpfe.Dto.ProfDto;
import com.pfe.projetpfe.entity.AppRole;
import com.pfe.projetpfe.entity.Filiere;
import com.pfe.projetpfe.entity.Professeur;
import com.pfe.projetpfe.entity.TypeRole;
import com.pfe.projetpfe.repository.FiliereRepository;
import com.pfe.projetpfe.repository.ProfesseurRepository;
import com.pfe.projetpfe.repository.RoleRepository;
import com.pfe.projetpfe.service.ProfServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;


@Controller("/api/admin")
@RequiredArgsConstructor
public class adminController {

    private final RoleRepository roleRepository;
    private ProfServiceImp profService;
    private ProfesseurRepository professeurRepository;
    private FiliereRepository filiereRepository;



    @RequestMapping(path = "/addNewProfsseur")
    public ResponseEntity<?> AddNewProfesseur(@RequestBody Professeur professeur) {
        if(professeurRepository.findByEmail(professeur.getEmail()) != null){
            return ResponseEntity.badRequest().body(professeur.getEmail()+"deja exist");
        }

        Professeur newProfesseur = profService.encryptPassword(professeur);

        AppRole role= roleRepository.findByroleName(TypeRole.PROFESSEUR);
        newProfesseur.setRole(role);
        professeurRepository.save(newProfesseur);

        Professeur professeurAjouter = new Professeur();
        professeurAjouter.setId(newProfesseur.getId());
        professeurAjouter.setNom(newProfesseur.getNom());
        professeurAjouter.setPrenom(professeur.getPrenom());
        professeurAjouter.setEmail(newProfesseur.getEmail());

        return ResponseEntity.ok().body(professeurAjouter);

    }
    @PutMapping(path = "/UpdateProfesseur")
    public ResponseEntity<?> updateProfesseur(@RequestBody Professeur professeur) throws Exception {
        Optional<Professeur> existingProfesseur = professeurRepository.findById(professeur.getId());
        if (!existingProfesseur.isPresent()) {
            throw new Exception("Professeur not found with ID: " + professeur.getId());
        }

        // Update logic
        Professeur updatedProfesseur = existingProfesseur.get();
        updatedProfesseur.setNom(professeur.getNom());
        updatedProfesseur.setPrenom(professeur.getPrenom());
        updatedProfesseur.setEmail(professeur.getEmail());

        professeurRepository.save(updatedProfesseur);

        return ResponseEntity.ok().body(updatedProfesseur);
    }

    @DeleteMapping(path = "DeleteProfesseur/id")
    public ResponseEntity<?> DeleteProfesseur(@PathVariable Long id) throws Exception {
        Optional professeur = professeurRepository.findById(id);
        if (!professeur.isPresent()) {
            throw new Exception("Professeur not found with ID: " + id);
        }
        professeurRepository.deleteById(id);
        return ResponseEntity.ok().body("Professeur deleted");
    }

    @GetMapping(path = "/ListProfesseurs")
    public ResponseEntity<?> ListProfesseurs() {
        List<ProfDto> professeursDto = profService.getAllProfs();

        if (professeursDto.isEmpty()) {
            // Retourner une réponse avec un code 400 si la liste est vide
            return ResponseEntity.badRequest().body("Aucun professeur trouvé");
        }

        return ResponseEntity.ok(professeursDto);
    }

    @GetMapping(path = "/getProfesseur/nom")
    public ResponseEntity<?> ChercheProfesseur(@PathVariable String nom) throws Exception {
        ProfDto professeur =profService.getProfByName(nom);
        if (professeur==null) {
            throw new Exception("Professeur " + nom+" n'exist pas");
        }
        return ResponseEntity.ok().body(professeur);
    }


}
