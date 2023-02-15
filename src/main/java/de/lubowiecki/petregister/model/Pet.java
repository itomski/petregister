package de.lubowiecki.petregister.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String race;

    private String chipId;

    private String name;

    private LocalDate birthDate;

    // Falls Owner noch nicht in der DB ist, würde er persistiert (gespeichert) werden
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Owner owner;

    @Enumerated(EnumType.STRING)
    private Status status;
}
