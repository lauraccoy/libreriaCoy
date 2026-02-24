package com.libreriacoy.controller.api;

import com.libreriacoy.dto.DetalleViewDTO;
import com.libreriacoy.service.DetalleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/detalle")
public class DetalleApiController {

    private final DetalleService detalleService;

    public DetalleApiController(DetalleService detalleService) {
        this.detalleService = detalleService;
    }

    // GET /api/detalle?tipo=Libro&id=1
    @GetMapping
    public DetalleViewDTO obtenerDetalle(@RequestParam String tipo, @RequestParam Long id) {
        DetalleViewDTO dto = detalleService.obtenerDetalle(tipo, id);
        if (dto == null) return null;

        String img = dto.getImg();
        if (img != null && !img.isBlank()) {
            if (!img.startsWith("http")) {
                // Aseguramos formato "/portadas/x.jpg" y construimos URL absoluta
                String path = img.startsWith("/") ? img : "/" + img;
                String url = org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath().path(path).toUriString();
                dto.setImg(url);
            }
        }
        return dto;
    }
}
