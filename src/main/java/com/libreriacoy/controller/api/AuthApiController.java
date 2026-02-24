package com.libreriacoy.controller.api;

import com.libreriacoy.dto.auth.AuthResponse;
import com.libreriacoy.dto.auth.LoginRequest;
import com.libreriacoy.dto.auth.RegisterRequest;
import com.libreriacoy.repository.AppUserRepository;
import com.libreriacoy.security.JwtService;
import com.libreriacoy.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    private final AuthService authService;
    private final AppUserRepository userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthApiController(
            AuthService authService,
            AppUserRepository userRepo,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.authService = authService;
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        try {
            final var u = authService.register(req.getNombre(), req.getEmail(), req.getPassword());
            final String token = jwtService.generateToken(u.getEmail(), u.getRole().name());
            return ResponseEntity.ok(new AuthResponse(token, u.getEmail(), u.getRole().name()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail().trim().toLowerCase(), req.getPassword())
            );

            final var u = userRepo.findByEmail(auth.getName())
                    .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

            final String token = jwtService.generateToken(u.getEmail(), u.getRole().name());
            return ResponseEntity.ok(new AuthResponse(token, u.getEmail(), u.getRole().name()));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Email o contraseña incorrectos");
        }
    }
}
