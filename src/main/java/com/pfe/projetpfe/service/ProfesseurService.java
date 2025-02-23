package com.pfe.projetpfe.service;

import com.pfe.projetpfe.Dto.*;


import java.util.List;

public interface ProfesseurService {
    //gestion mudole
    List<ReturnModuleDto> findModuleByProfesseurId(Long professeurId);
    ReturnModuleDto addModule(AddModuleDto ajouterDto);
    ReturnModuleDto updateModule(ReturnModuleDto returnModuleDto);

    //gestion resources
    ResourceReturnDto addResources(AddResourceDto ajouteResourceDto) throws Exception;
    ResourceReturnDto updateResources(UpdateResourceDto updateResourceDto ) throws Exception;
}

