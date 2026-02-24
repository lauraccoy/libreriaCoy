package com.libreriacoy.controller;

/*
 * Archivo: ComicController.java
 * Paquete: com.libreriacoy.controller
 * Propósito: Controlador MVC: gestiona rutas HTTP, prepara el Model y devuelve vistas Thymeleaf.
 * Nota: Comentarios añadidos para documentación y mantenimiento (2026-01-23).
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.libreriacoy.repository.ComicRepository;

@Controller
/**
 * ComicController.
 *
 * Controlador MVC: gestiona rutas HTTP, prepara el Model y devuelve vistas Thymeleaf.
 */
public class ComicController {

    @Autowired
    private ComicRepository comicRepository;

    @GetMapping("/comics")
    public String verComics(Model model) {
        System.out.println("Accediendo a comics");
        model.addAttribute("comics", comicRepository.findAll());
        model.addAttribute("titulo", "Cómics");
        model.addAttribute("contenido", "comics");
        return "layout";
    }
}

