package com.pfe.projetpfe.service;

import com.pfe.projetpfe.Dto.ModuleAjouterDto;
import com.pfe.projetpfe.Dto.ReturnModuleDto;


import java.util.List;

public interface ProfesseurService {
    List<ReturnModuleDto> findModuleByProfesseurId(Long professeurId);
    ReturnModuleDto addModule(ModuleAjouterDto ajouterDto);
    ReturnModuleDto updateModule(ReturnModuleDto returnModuleDto);
}

