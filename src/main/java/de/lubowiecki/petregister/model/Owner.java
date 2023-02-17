package de.lubowiecki.petregister.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Owner implements UserDetails, Serializable { // UserDetails entspreicht einem User, der sich in SpringSecurity anmelden kann

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    /*
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(columnDefinition = "BINARY(16)")
    */
    private UUID id;

    private String firstname;

    private String lastname;

    @Column(unique = true)
    private String email;

    @JsonIgnore // Versteckt dieses Attribut bei der Ausgabe als JSON
    private String password;

    // Alles was mit Owner passiert, wird weiter an dazugehörige Pet-Entities weitergegeben
    // orphanRemoval: beim Löschen des Owners werden auch Pet-Datensätze aus der DB gelöscht
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Pet> pets = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // SimpleGrantedAuthority ist eine einfach Implementierung des GrantedAuthority-Interface
        return Set.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() { // Anmeldename
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // ist der Account NICHT abgelaufen?
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // ist der Account NICHT gesperrt?
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // sind die Zugangsdaten noch aktuell?
    }

    @Override
    public boolean isEnabled() {
        return true; // ist der Account aktiv?
    }
}
