package com.libreriacoy.controller;

/*
 * Archivo: CatalogoController.java
 * Paquete: com.libreriacoy.controller
 * Propósito: Controlador MVC: gestiona rutas HTTP, prepara el Model y devuelve vistas Thymeleaf.
 * Nota: Comentarios añadidos para documentación y mantenimiento (2026-01-23).
 */


import com.libreriacoy.service.CatalogoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// Esta clase es un controlador que maneja la página del catálogo.
// Recibe peticiones del navegador y decide qué datos mostrar.
@Controller
/**
 * CatalogoController.
 *
 * Controlador MVC: gestiona rutas HTTP, prepara el Model y devuelve vistas Thymeleaf.
 */
public class CatalogoController {

    // Variable para acceder al servicio del catálogo.
    private final CatalogoService catalogoService;

    // Constructor que recibe el servicio. Spring lo inyecta automáticamente.
    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    // Este método se ejecuta cuando alguien visita "/catalogo".
    // Obtiene todos los productos y los pasa a la vista.
    @GetMapping("/catalogo")
    public String verCatalogo(Model model) {
        // Imprime un mensaje en la consola.
        System.out.println("Accediendo a catalogo");
        // Agrega el título de la página.
        model.addAttribute("titulo", "Catálogo General");
        // La vista "catalogo.html" usa el atributo "productos".
        // (Dejamos también "items" por compatibilidad, por si otras vistas lo usan.)
        model.addAttribute("productos", catalogoService.obtenerCatalogoCompleto());
        model.addAttribute("items", catalogoService.obtenerCatalogoCompleto());

        // Indica qué contenido cargar en el layout (catalogo.html).
        model.addAttribute("contenido", "catalogo");

        return "layout";
    }
}
