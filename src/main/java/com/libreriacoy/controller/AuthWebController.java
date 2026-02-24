package com.libreriacoy.controller;

import com.libreriacoy.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthWebController {

    private final AuthService authService;

    public AuthWebController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        model.addAttribute("titulo", "Iniciar sesión");
        model.addAttribute("contenido", "login");
        return "layout";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("errorMessage", null);
        model.addAttribute("titulo", "Crear cuenta");
        model.addAttribute("contenido", "register");
        return "layout";
    }

    @PostMapping("/register")
    public String doRegister(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String password,
            Model model,
            HttpServletRequest request
    ) {
        try {
            authService.register(nombre, email, password);
            // Redirigimos al login (Spring Security gestiona el login con sesión)
            return "redirect:/login";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("titulo", "Crear cuenta");
            model.addAttribute("contenido", "register");
            return "layout";
        }
    }
}
