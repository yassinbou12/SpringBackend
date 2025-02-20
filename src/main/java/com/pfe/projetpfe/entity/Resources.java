package com.pfe.projetpfe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Resources {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResource;
    private String nom;
    @Enumerated(EnumType.STRING)
    private ResourcesType type;
    @Enumerated(EnumType.STRING)
    private DataType dataType;
    @Lob
    private byte[] data;
    private String lien;
    @ManyToOne
    @JoinColumn(name="moduleId")
    private Module module;



}
