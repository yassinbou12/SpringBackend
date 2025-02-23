package com.pfe.projetpfe.Dto;

import com.pfe.projetpfe.entity.DataType;
import com.pfe.projetpfe.entity.ResourcesType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class UpdateResourceDto {
    private Long id;
    private String nom;
    @Enumerated(EnumType.STRING)
    private ResourcesType type;
    @Enumerated(EnumType.STRING)
    private DataType dataType;
    private MultipartFile data;
    private String lien;
    private Long moduleId;
    private Long professorId;

}
