package com.pfe.projetpfe.Dto;

import com.pfe.projetpfe.entity.DataType;
import com.pfe.projetpfe.entity.ResourcesType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class AddResourceDto {
    private String nom;
    @Enumerated(EnumType.STRING)
    private ResourcesType type;
    @Enumerated(EnumType.STRING)
    private DataType dataType;
    private MultipartFile data;
    private String lien;
    private Long moduleId;

}
