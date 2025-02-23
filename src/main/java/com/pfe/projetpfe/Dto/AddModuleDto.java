package com.pfe.projetpfe.Dto;

import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddModuleDto {
    Long idProfesseur;
    String name;
    String semestre;
    String filiereName;
    @Max(9000)
    String description;
}
