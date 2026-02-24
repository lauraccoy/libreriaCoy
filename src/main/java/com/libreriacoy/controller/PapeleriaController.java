package com.libreriacoy.controller;

/*
 * Archivo: PapeleriaController.java
 * Paquete: com.libreriacoy.controller
 * Propósito: Controlador MVC: gestiona rutas HTTP, prepara el Model y devuelve vistas Thymeleaf.
 * Nota: Comentarios añadidos para documentación y mantenimiento (2026-01-23).
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.libreriacoy.repository.PapeleriaRepository;

@Controller
/**
 * PapeleriaController.
 *
 * Controlador MVC: gestiona rutas HTTP, prepara el Model y devuelve vistas Thymeleaf.
 */
public class PapeleriaController {

    @Autowired
    private PapeleriaRepository papeleriaRepository;

    @GetMapping("/papeleria")
    public String papeleria(Model model) {

        System.out.println("Accediendo a papeleria");
        model.addAttribute("titulo", "Papelería");
        
        // 🔴 ESTO ES LO CLAVE
        model.addAttribute("contenido", "papeleria");

        // Lista para el th:each
        model.addAttribute("papeleria", papeleriaRepository.findAll());

        return "layout";
    }
}
