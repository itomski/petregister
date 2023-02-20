package de.lubowiecki.petregister.controller;

import de.lubowiecki.petregister.exception.PetNotFoundException;
import de.lubowiecki.petregister.model.Pet;
import de.lubowiecki.petregister.repository.OwnerRepository;
import de.lubowiecki.petregister.repository.PetRepository;
import de.lubowiecki.petregister.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping("api/v1/pets")
@CrossOrigin
@RequiredArgsConstructor
@Transactional
public class PetController {

    private final PetRepository petRepository; // Bei final Feldern ist eine Constructor-Injection nötig

    private final OwnerRepository ownerRepository;

    @GetMapping
    public List<Pet> index() {
        return petRepository.findAll();
    }

    @GetMapping("{chipId}")
    public Pet byChipId(@PathVariable String chipId) {
        return petRepository.findByChipId(chipId).orElseThrow(() -> new PetNotFoundException());
    }

    @PostMapping
    public Pet insert(@RequestBody Pet pet) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var opt = ownerRepository.findByEmail(userDetails.getUsername());
        if(opt.isPresent()) {
            var owner = opt.get();
            pet.setOwner(owner);
        }
        return petRepository.save(pet);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable String id) {

        UUID uuid = new UUID(
                new BigInteger(id.substring(0,16), 16).longValue(),
                new BigInteger(id.substring(16), 16).longValue());

        petRepository.deleteById(uuid);
        return "{ \"deleted\": true}";
    }

    @PutMapping("{id}")
    public Pet update(@PathVariable String id, @RequestBody Pet pet) {

        UUID uuid = new UUID(
                new BigInteger(id.substring(0,16), 16).longValue(),
                new BigInteger(id.substring(16), 16).longValue());

        var opt = petRepository.findById(uuid);
        if(opt.isPresent()) {
            var dbPet = opt.get();
            dbPet.setChipId(pet.getChipId());
            dbPet.setName(pet.getName());
            dbPet.setBirthDate(pet.getBirthDate());
            dbPet.setRace(pet.getRace());
            dbPet.setStatus(pet.getStatus());
            return petRepository.save(dbPet);
        }
        return new Pet();
    }
}
