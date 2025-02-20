package com.pfe.projetpfe.Dto;

import com.pfe.projetpfe.entity.DataType;
import com.pfe.projetpfe.entity.ResourcesType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResourceReturnDto {
    private Long id;
    private String nom;
    private ResourcesType type;
    private DataType dataType;
    private byte[] data;
    private String lien;
    private String moduleName;
    private String filiereName;
}
