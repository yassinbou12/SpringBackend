package com.pfe.projetpfe.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ModuleAjouterDto {
    Long idProfesseur;
    String name;
    String semestre;
    String filiereName;
}
