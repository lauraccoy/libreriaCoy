package com.libreriacoy.service;

import com.libreriacoy.model.auth.AppUser;
import com.libreriacoy.model.auth.Role;
import com.libreriacoy.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AppUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AppUserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser register(String nombre, String email, String rawPassword) {
        final String normalizedEmail = email.trim().toLowerCase();
        if (userRepo.existsByEmail(normalizedEmail)) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }

        final String hash = passwordEncoder.encode(rawPassword);
        final AppUser u = new AppUser(nombre.trim(), normalizedEmail, hash, Role.USER);
        return userRepo.save(u);
    }
}
