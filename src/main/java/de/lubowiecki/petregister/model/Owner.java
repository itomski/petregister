package de.lubowiecki.petregister.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstname;

    private String lastname;

    @Column(unique = true)
    private String email;

    private String password;

    // Alles was mit Owner passiert, wird weiter an dazugehörige Pet-Entities weitergegeben
    // orphanRemoval: beim Löschen des Owners werden auch Pet-Datensätze aus der DB gelöscht
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Pet> pets = new HashSet<>();
}
