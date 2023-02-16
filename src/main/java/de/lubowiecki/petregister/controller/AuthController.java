package de.lubowiecki.petregister.controller;

import de.lubowiecki.petregister.model.AuthRequest;
import de.lubowiecki.petregister.model.AuthResponse;
import de.lubowiecki.petregister.model.Owner;
import de.lubowiecki.petregister.repository.OwnerRepository;
import de.lubowiecki.petregister.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    public final AuthenticationManager authenticationManager;

    public final OwnerRepository ownerRepository;

    public final JwtService jwtService;

    @PostMapping("login")
    public AuthResponse authenticate(@RequestBody AuthRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Owner owner = ownerRepository.findByEmail(request.getEmail())
                                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(owner);

        return new AuthResponse(token);
    }
}
