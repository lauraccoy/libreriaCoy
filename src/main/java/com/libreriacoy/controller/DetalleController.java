package com.libreriacoy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.libreriacoy.dto.DetalleViewDTO;
import com.libreriacoy.service.DetalleService;

@Controller
public class DetalleController {

    private final DetalleService detalleService;

    public DetalleController(DetalleService detalleService) {
        this.detalleService = detalleService;
    }

    // /detalle?tipo=LIBRO&id=1  o  /detalle?tipo=Libro&id=1
    @GetMapping("/detalle")
    public String verDetalle(@RequestParam String tipo,
                             @RequestParam Long id,
                             Model model) {
        return renderDetalle(tipo, id, model);
    }

    // /detalle/LIBRO/1  o  /detalle/Libro/1
    @GetMapping("/detalle/{tipo}/{id}")
    public String verDetallePath(@PathVariable String tipo,
                                 @PathVariable Long id,
                                 Model model) {
        return renderDetalle(tipo, id, model);
    }

    private String renderDetalle(String tipo, Long id, Model model) {
        String tipoNorm = normalizarTipo(tipo);

        System.out.println("Accediendo a detalle con tipo=" + tipoNorm + " id=" + id);

        DetalleViewDTO detalle = detalleService.obtenerDetalle(tipoNorm, id);

        // Debug útil
        System.out.println("DETALLE OBJ -> " + (detalle != null ? detalle.getTitulo() : "NULL"));

        model.addAttribute("detalle", detalle);
        model.addAttribute("tipo", tipoNorm);
        model.addAttribute("id", id);
        model.addAttribute("titulo", "Detalle");
        model.addAttribute("contenido", "detalle");

        return "layout";
    }

    // Normaliza entradas tipo: "LIBRO", "Libro", "libro", "Papelería", "PAPELERIA", etc.
    private String normalizarTipo(String tipo) {
        if (tipo == null) return "";
        String t = tipo.trim().toUpperCase();

        // quitar tildes típicas por si llega "PAPELERÍA"
        t = t.replace("Í", "I").replace("Á", "A").replace("É", "E").replace("Ó", "O").replace("Ú", "U");

        // Mapear a nombres canónicos que usará el Service
        return switch (t) {
            case "LIBRO" -> "LIBRO";
            case "COMIC", "CÓMIC" -> "COMIC";
            case "PAPELERIA", "PAPELERÍA" -> "PAPELERIA";
            case "GEEK" -> "GEEK";
            default -> t;
        };
    }
}
