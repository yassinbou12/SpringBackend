package com.pfe.projetpfe.Doa;

import com.pfe.projetpfe.repository.ModuleRepositorie;
import org.springframework.beans.factory.annotation.Autowired;

public class ModuleService {


    private ModuleRepositorie moduleRepositorie;

    public ModuleService(ModuleRepositorie moduleRepositorie) {
        this.moduleRepositorie = moduleRepositorie;
    }

}
