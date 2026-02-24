package com.libreriacoy.security;

import com.libreriacoy.model.auth.AppUser;
import com.libreriacoy.repository.AppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository userRepo;

    public UserDetailsServiceImpl(AppUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String email = username.trim().toLowerCase();

        AppUser u = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No existe: " + email));

        return new org.springframework.security.core.userdetails.User(
                u.getEmail(),
                u.getPasswordHash(), // ✅ CLAVE
                List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name()))
        );
    }
}
