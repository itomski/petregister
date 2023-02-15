package de.lubowiecki.petregister.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean // Beans können über Konstruktoren oder @Autowired eingebunden werden
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
