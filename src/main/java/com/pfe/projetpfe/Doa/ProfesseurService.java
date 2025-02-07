package com.pfe.projetpfe.Doa;

import com.pfe.projetpfe.repository.ProfesseurRepository;

public class ProfesseurService {


    private ProfesseurRepository professeurRepository;

    public ProfesseurService(ProfesseurRepository professeurRepository) {
        this.professeurRepository = professeurRepository;
    }


}
