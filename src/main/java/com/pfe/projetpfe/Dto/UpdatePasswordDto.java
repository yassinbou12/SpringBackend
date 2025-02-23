package com.pfe.projetpfe.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdatePasswordDto {
    private Long id;
    private String oldPassword;
    private String newPassword;
}
