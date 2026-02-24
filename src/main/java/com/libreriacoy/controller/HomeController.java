package com.libreriacoy.controller;

/*
 * Archivo: HomeController.java
 * Paquete: com.libreriacoy.controller
 * Propósito: Controlador MVC: gestiona rutas HTTP, prepara el Model y devuelve vistas Thymeleaf.
 * Nota: Comentarios añadidos para documentación y mantenimiento (2026-01-23).
 */


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// Esta clase es un controlador que maneja las páginas web relacionadas con la página de inicio.
// Un controlador recibe las peticiones del navegador y decide qué mostrar.
@Controller
/**
 * HomeController.
 *
 * Controlador MVC: gestiona rutas HTTP, prepara el Model y devuelve vistas Thymeleaf.
 */
public class HomeController {

    // Este método se ejecuta cuando alguien visita la URL "/" (página principal) o "/home".
    // Devuelve el nombre de la plantilla HTML a mostrar.
    @GetMapping({"/", "/home"})
    public String home(Model model) {
        // Imprime un mensaje en la consola para saber que se accedió a esta página.
        System.out.println("Accediendo a home");
        // Agrega el título de la página al modelo, que se usará en la vista.
        model.addAttribute("titulo", "Inicio");
        // Indica qué contenido cargar dentro del layout (en este caso, home.html).
        model.addAttribute("contenido", "home"); // <- IMPORTANTE: carga home.html dentro del layout
        // Devuelve "layout", que es la plantilla principal que incluye el contenido.
        return "layout";
    }
}
