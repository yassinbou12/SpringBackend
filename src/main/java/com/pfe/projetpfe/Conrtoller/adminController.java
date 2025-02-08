package com.pfe.projetpfe.Conrtoller;


import com.pfe.projetpfe.entity.Professeur;
import com.pfe.projetpfe.repository.ProfesseurRepository;
import com.pfe.projetpfe.service.ProfServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller("/api/admin")
@RequiredArgsConstructor
public class adminController {

    private ProfServiceImp profService;
    private ProfesseurRepository professeurRepository;



    @RequestMapping(path = "/addNewProfsseur")
    public ResponseEntity<?> AddNewProfesseur(@RequestBody Professeur professeur) {
        if(professeurRepository.findByEmail(professeur.getEmail()) != null){
            return ResponseEntity.badRequest().body(professeur.getEmail()+"deja exist");
        }
        Professeur newProfesseur = profService.encryptPassword(professeur);


        professeurRepository.save(newProfesseur);

        Professeur professeurAjouter = new Professeur();
        professeurAjouter.setId(newProfesseur.getId());
        professeurAjouter.setNom(newProfesseur.getNom());
        professeurAjouter.setPrenom(professeur.getPrenom());
        professeurAjouter.setEmail(newProfesseur.getEmail());

        return ResponseEntity.ok().body(professeurAjouter);


    }

}
