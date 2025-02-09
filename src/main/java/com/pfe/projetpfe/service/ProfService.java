package com.pfe.projetpfe.service;

import com.pfe.projetpfe.Dto.ProfDto;
import com.pfe.projetpfe.entity.Professeur;

import java.util.List;

public interface ProfService {
    Professeur encryptPassword(Professeur user);
    List<ProfDto> getAllProfs();
    ProfDto getProfByName(String name);
}
