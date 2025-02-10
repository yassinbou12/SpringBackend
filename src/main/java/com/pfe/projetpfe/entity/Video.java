package com.pfe.projetpfe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video extends Resources{
    private String lien;
    @ManyToOne
    @JoinColumn(name="moduleId")
    private Module module;
}
