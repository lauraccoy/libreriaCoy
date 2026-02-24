package com.libreriacoy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsServiceImpl userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .authorizeHttpRequests(auth -> auth

                // ✅ estáticos
                .requestMatchers(
                    "/css/**", "/js/**", "/images/**", "/webjars/**",
                    "/portadas/**", "/papeleria/**", "/geek/**"
                ).permitAll()

                // ✅ páginas públicas
                .requestMatchers(
                    "/", "/home",
                    "/catalogo", "/libros", "/comics", "/papeleria", "/geek",
                    "/detalle/**",

                    // ✅ (PUNTO 7) blog público
                    "/blog", "/blog/**"
                ).permitAll()

                // ✅ auth pages
                .requestMatchers("/login", "/register", "/auth/**").permitAll()

                // ✅ API pública (según tu diseño)
                .requestMatchers(
                	    "/api/auth/**",
                	    "/api/catalogo/**",
                	    "/api/detalle/**",
                	    "/api/blog/**"
                	).permitAll()


                // ✅ carrito público
                .requestMatchers("/carrito", "/carrito/**").permitAll()

                // ✅ checkout requiere login
                .requestMatchers(
                    "/checkout", "/checkout/**",
                    "/pedido", "/pedido/**",
                    "/finalizar-compra", "/finalizar-compra/**"
                ).authenticated()

                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                // ✅ por defecto usa "username" + "password"
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
            );

        // ✅ JWT solo afecta /api/** (tu filtro ya lo limita)
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        // ✅ CLAVE: usar provider de BD (UserDetailsServiceImpl + BCrypt)
        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
