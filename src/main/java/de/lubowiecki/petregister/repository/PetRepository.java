package de.lubowiecki.petregister.repository;

import de.lubowiecki.petregister.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PetRepository extends JpaRepository<Pet, UUID> {
}
