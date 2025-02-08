package com.pfe.projetpfe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class Fichier extends Resources{
    @Lob
    private byte[] data;
}
