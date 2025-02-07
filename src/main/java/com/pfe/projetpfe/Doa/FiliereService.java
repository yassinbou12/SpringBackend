package com.pfe.projetpfe.Doa;

import com.pfe.projetpfe.repository.FiliereRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class FiliereService {



    private FiliereRepository filiereRepository;

    public FiliereService(FiliereRepository filiereRepository) {
        this.filiereRepository = filiereRepository;
    }

}
