package com.pfe.projetpfe.Dto;

import com.pfe.projetpfe.entity.DataType;
import com.pfe.projetpfe.entity.ResourcesType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResourceReturnDto {
    private Long id;
    private String nom;
    @Enumerated(EnumType.STRING)
    private ResourcesType type;
    @Enumerated(EnumType.STRING)
    private DataType dataType;
    private String lien;
    private String moduleName;
    private Long moduleId;
    private Long professorId;
    private String filiereName;
}
