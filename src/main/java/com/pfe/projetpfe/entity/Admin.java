package com.pfe.projetpfe.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "admin")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends Personne {
    @Enumerated(EnumType.STRING)
    private TypeRole role=TypeRole.ROLE_ADMIN;
}
