package com.pfe.projetpfe.service;

import com.pfe.projetpfe.Dto.ReturnModuleDto;

import java.util.List;

public interface ProfesseurService {
    List<ReturnModuleDto> findByProfesseurId(Long professeurId);
}

