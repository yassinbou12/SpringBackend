package com.pfe.projetpfe.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfDto {
    Long id;
    String nom;
    String prenom;
    String email;
}
