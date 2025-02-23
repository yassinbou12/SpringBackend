package com.pfe.projetpfe.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnModuleDto {
    private Long id;
    private String name;
    private String Semestre;
    private String FiliereName;
    private String description;
}
