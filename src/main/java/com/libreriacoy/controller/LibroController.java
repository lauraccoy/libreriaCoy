package com.libreriacoy.controller;

/*
 * Archivo: LibroController.java
 * Paquete: com.libreriacoy.controller
 * Propósito: Controlador MVC: gestiona rutas HTTP, prepara el Model y devuelve vistas Thymeleaf.
 * Nota: Comentarios añadidos para documentación y mantenimiento (2026-01-23).
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.libreriacoy.repository.LibroRepository;

@Controller
/**
 * LibroController.
 *
 * Controlador MVC: gestiona rutas HTTP, prepara el Model y devuelve vistas Thymeleaf.
 */
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @GetMapping("/libros")
    public String verLibros(Model model) {
        System.out.println("Accediendo a libros");
        model.addAttribute("libros", libroRepository.findAll());
        model.addAttribute("titulo", "Libros");
        model.addAttribute("contenido", "libros");   // 👈 importante
        return "layout";
    }

}
