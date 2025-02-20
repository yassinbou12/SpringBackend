package com.pfe.projetpfe.Dto;

import com.pfe.projetpfe.entity.DataType;
import com.pfe.projetpfe.entity.ResourcesType;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class AddResourceDto {
    private String nom;
    private ResourcesType type;
    private DataType dataType;
    private MultipartFile data;
    private String lien;
    private Long moduleId;

}
