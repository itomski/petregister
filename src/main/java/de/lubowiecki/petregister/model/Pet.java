package de.lubowiecki.petregister.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    /*
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(columnDefinition = "BINARY(16)")
    */
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String race;

    private String chipId;

    private String name;

    private LocalDate birthDate;

    // Falls Owner noch nicht in der DB ist, würde er persistiert (gespeichert) werden
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Owner owner;

    @Enumerated(EnumType.STRING)
    private Status status;
}
