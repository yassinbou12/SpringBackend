package com.pfe.projetpfe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Fichier extends Resources{
    @Lob
    private byte[] data;
    @ManyToOne
    @JoinColumn(name="moduleId")
    private Module module;
}
