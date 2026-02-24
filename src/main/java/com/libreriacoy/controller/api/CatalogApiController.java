package com.libreriacoy.controller.api;

import com.libreriacoy.dto.ItemCatalogo;
import com.libreriacoy.service.CatalogoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo")
public class CatalogApiController {

    private final CatalogoService catalogoService;

    public CatalogApiController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    // GET /api/catalogo
    @GetMapping
    public List<ItemCatalogo> obtenerCatalogo() {
        List<ItemCatalogo> items = catalogoService.obtenerCatalogoCompleto();
        // convert image paths to absolute URLs
        for (ItemCatalogo it : items) {
            String img = it.getImagenUrl();
            if (img != null && !img.startsWith("http")) {
                img = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                        .fromCurrentContextPath().path(img).toUriString();
            }
            it.setImagen(img);
        }
        return items;
    }

    // GET /api/catalogo/{tipo}/{id}
    @GetMapping("/{tipo}/{id}")
    public ItemCatalogo obtenerItem(@PathVariable String tipo, @PathVariable Long id) {
        ItemCatalogo item = catalogoService.obtenerItem(tipo, id);
        if (item == null) return null;
        String img = item.getImagenUrl();
        if (img != null && !img.startsWith("http")) {
            img = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                    .fromCurrentContextPath().path(img).toUriString();
        }
        item.setImagen(img);
        return item;
    }
}
