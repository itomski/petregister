package de.lubowiecki.petregister.repository;

import de.lubowiecki.petregister.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PetRepository extends JpaRepository<Pet, UUID> {
    Optional<Pet> findByChipId(String chipId);
}
