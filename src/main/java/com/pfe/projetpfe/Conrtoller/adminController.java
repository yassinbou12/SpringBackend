package com.pfe.projetpfe.Conrtoller;

import com.pfe.projetpfe.Doa.ProfesseurService;
import com.pfe.projetpfe.Dto.UserDto;
import com.pfe.projetpfe.entity.Admin;
import com.pfe.projetpfe.entity.Professeur;
import com.pfe.projetpfe.repository.AdminRepository;
import com.pfe.projetpfe.repository.ProfesseurRepository;
import com.pfe.projetpfe.service.ProfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller()
public class adminController {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ProfesseurRepository professeurRepository;

    @Autowired
    Professeur professeur;


    @RequestMapping(path = "/admin/addProfsseur")
    public ResponseEntity<Professeur> AddProfesseur(@RequestBody Professeur professeur) {
        profService.encryptPassword(userDto);

        professeurRepository.save(userDto);
    }

}
