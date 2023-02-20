package de.lubowiecki.petregister.controller;

import de.lubowiecki.petregister.exception.OwnerAlreadyExistsException;
import de.lubowiecki.petregister.exception.OwnerNotFoundException;
import de.lubowiecki.petregister.model.AuthRequest;
import de.lubowiecki.petregister.model.AuthResponse;
import de.lubowiecki.petregister.model.Owner;
import de.lubowiecki.petregister.model.RegisterRequest;
import de.lubowiecki.petregister.repository.OwnerRepository;
import de.lubowiecki.petregister.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
//@CrossOrigin(origins = {"","",""}) // Bei mehreren Werten
//@CrossOrigin(origins = "") // bei einem Wert
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    public final AuthenticationManager authenticationManager;

    public final OwnerRepository ownerRepository;

    public final JwtService jwtService;

    public final PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public AuthResponse authenticate(@RequestBody AuthRequest request) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        }
        catch(Exception ex) {
            throw new OwnerNotFoundException();
        }

        Owner owner = ownerRepository.findByEmail(request.getEmail())
                                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(owner);

        return new AuthResponse(token);
    }

    @PostMapping("register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {

        var opt = ownerRepository.findByEmail(request.getEmail());
        if(opt.isPresent()) {
            throw new OwnerAlreadyExistsException();
        }

        Owner owner = Owner.builder()
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .firstname(request.getFistname())
                        .lastname(request.getLastname())
                        .build();

        ownerRepository.save(owner);
        String token = jwtService.generateToken(owner);

        return new AuthResponse(token);
    }
}
