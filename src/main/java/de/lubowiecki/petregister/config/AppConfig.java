package de.lubowiecki.petregister.config;

import de.lubowiecki.petregister.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final OwnerRepository ownerRepository;

    @Bean // Beans können über Konstruktoren oder @Autowired eingebunden werden
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> ownerRepository.findByEmail(username) // Muss UserDetails zurückgeben
                                            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }
}
