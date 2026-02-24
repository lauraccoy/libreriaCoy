package com.libreriacoy.config;

import com.libreriacoy.model.auth.AppUser;
import com.libreriacoy.model.auth.Role;
import com.libreriacoy.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    private static final String ADMIN_EMAIL = "admin@libreriacoy.es";
    private static final String ADMIN_PASSWORD = "Emma-060304";

    // ⚠️ Ponlo a true SOLO cuando quieras forzar cambio de pass al arrancar
    private static final boolean RESET_ADMIN_PASSWORD = true;

    @Bean
    CommandLineRunner initAdmin(AppUserRepository userRepo, PasswordEncoder encoder) {
        return args -> {

            AppUser u = userRepo.findByEmail(ADMIN_EMAIL).orElse(null);

            if (u == null) {
                u = new AppUser("Admin", ADMIN_EMAIL, encoder.encode(ADMIN_PASSWORD), Role.USER);
                userRepo.save(u);
                System.out.println("✅ Admin creado: " + ADMIN_EMAIL);
            } else if (RESET_ADMIN_PASSWORD) {
                u.setPasswordHash(encoder.encode(ADMIN_PASSWORD));
                userRepo.save(u);
                System.out.println("✅ Admin actualizado (password reseteada): " + ADMIN_EMAIL);
            } else {
                System.out.println("ℹ️ Admin ya existe: " + ADMIN_EMAIL);
            }

            // Debug útil
            AppUser u2 = userRepo.findByEmail(ADMIN_EMAIL).orElseThrow();
            System.out.println("HASH LEN = " + u2.getPasswordHash().length());
            System.out.println("MATCHES = " + encoder.matches(ADMIN_PASSWORD, u2.getPasswordHash()));
        };
    }
}
