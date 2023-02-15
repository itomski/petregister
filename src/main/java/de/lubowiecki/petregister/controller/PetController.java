package de.lubowiecki.petregister.controller;

import de.lubowiecki.petregister.model.Pet;
import de.lubowiecki.petregister.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetRepository petRepository; // Bei final Feldern ist eine Constructor-Injection nötig

    @GetMapping
    public List<Pet> index() {
        return petRepository.findAll();
    }
}
