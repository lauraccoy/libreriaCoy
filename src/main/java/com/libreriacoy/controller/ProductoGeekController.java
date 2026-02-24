package com.libreriacoy.controller;

/*
 * Archivo: ProductoGeekController.java
 * Paquete: com.libreriacoy.controller
 * Propósito: Controlador MVC: gestiona rutas HTTP, prepara el Model y devuelve vistas Thymeleaf.
 * Nota: Comentarios añadidos para documentación y mantenimiento (2026-01-23).
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.libreriacoy.repository.ProductoGeekRepository;

@Controller
/**
 * ProductoGeekController.
 *
 * Controlador MVC: gestiona rutas HTTP, prepara el Model y devuelve vistas Thymeleaf.
 */
public class ProductoGeekController {

    @Autowired
    private ProductoGeekRepository productoGeekRepository;

    @GetMapping("/geek")
    public String verGeek(Model model) {
        System.out.println("Accediendo a geek");

        var lista = productoGeekRepository.findAll();

        // ✅ para tu template actual (usa "geek")
        model.addAttribute("geek", lista);

        // ✅ por si en algún template/controlador se usa "productosGeek"
        model.addAttribute("productosGeek", lista);

        model.addAttribute("titulo", "Geek");
        model.addAttribute("contenido", "geek");
        return "layout";
    }
}
