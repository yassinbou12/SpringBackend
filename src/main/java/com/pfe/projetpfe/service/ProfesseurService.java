package com.pfe.projetpfe.service;

import com.pfe.projetpfe.Dto.AddResourceDto;
import com.pfe.projetpfe.Dto.AddModuleDto;
import com.pfe.projetpfe.Dto.ResourceReturnDto;
import com.pfe.projetpfe.Dto.ReturnModuleDto;


import java.util.List;

public interface ProfesseurService {
    List<ReturnModuleDto> findModuleByProfesseurId(Long professeurId);
    ReturnModuleDto addModule(AddModuleDto ajouterDto);
    ReturnModuleDto updateModule(ReturnModuleDto returnModuleDto);

    ResourceReturnDto AddResources(AddResourceDto ajouteResourceDto) throws Exception;
}

