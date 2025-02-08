package com.pfe.projetpfe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Resources {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResource;

    private String nomResource;
    private ResourcesType resourceType;


}
