package com.libreriacoy.service;

/*
 * Archivo: CatalogoService.java
 * Paquete: com.libreriacoy.service
 * Propósito: Capa de servicio: contiene lógica de negocio y acceso unificado a repositorios.
 * Nota: Comentarios añadidos para documentación y mantenimiento (2026-01-23).
 */


import com.libreriacoy.dto.ItemCatalogo;
import com.libreriacoy.model.Comic;
import com.libreriacoy.model.Libro;
import com.libreriacoy.model.Papeleria;
import com.libreriacoy.model.ProductoGeek;
import com.libreriacoy.repository.ComicRepository;
import com.libreriacoy.repository.LibroRepository;
import com.libreriacoy.repository.PapeleriaRepository;
import com.libreriacoy.repository.ProductoGeekRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Esta clase es un servicio que maneja la lógica para el catálogo de productos.
// Un servicio contiene el "cerebro" de la aplicación, como obtener datos y procesarlos.
// @Service indica que esta clase es un servicio de Spring.
@Service
/**
 * CatalogoService.
 *
 * Capa de servicio: contiene lógica de negocio y acceso unificado a repositorios.
 */
public class CatalogoService {

    // Variables para acceder a los repositorios de cada tipo de producto.
    private final LibroRepository libroRepo;
    private final ComicRepository comicRepo;
    private final PapeleriaRepository papeleriaRepo;
    private final ProductoGeekRepository geekRepo;

    // Constructor que recibe los repositorios. Spring los inyecta automáticamente.
    public CatalogoService(LibroRepository libroRepo,
                           ComicRepository comicRepo,
                           PapeleriaRepository papeleriaRepo,
                           ProductoGeekRepository geekRepo) {
        this.libroRepo = libroRepo;
        this.comicRepo = comicRepo;
        this.papeleriaRepo = papeleriaRepo;
        this.geekRepo = geekRepo;
    }

    // Este método obtiene todos los productos de la base de datos y los combina en una lista de ItemCatalogo.
    // Devuelve una lista con todos los libros, cómics, papelería y productos geek.
    public List<ItemCatalogo> obtenerCatalogoCompleto() {
        // Crea una lista vacía para guardar los items del catálogo.
        List<ItemCatalogo> items = new ArrayList<>();

        // Recorre todos los libros y los agrega a la lista como ItemCatalogo.
        for (Libro l : libroRepo.findAll()) {
            items.add(new ItemCatalogo(
                    l.getId(),
                    l.getTitulo(),
                    l.getCategoria(),
                    "Libro",
                    l.getPrecio(),
                    // En BD guardamos solo el nombre del archivo.
                    // Para el catálogo unificado necesitamos la ruta relativa.
                    "portadas/" + l.getPortada()
            ));
        }

        for (Comic c : comicRepo.findAll()) {
            items.add(new ItemCatalogo(
                    c.getId(),
                    c.getTitulo(),
                    c.getCategoria(),
                    "Cómic",
                    c.getPrecio(),
                    "portadas/" + c.getPortada()
            ));
        }

        for (Papeleria p : papeleriaRepo.findAll()) {
            try {
                items.add(new ItemCatalogo(
                        p.getId(),
                        p.getNombre(),
                        p.getCategoria(),
                        "Papelería",
                        p.getPrecio(),
                        "papeleria/" + p.getImagen()
                ));
            } catch (Exception e) {
                System.err.println("Error al procesar Papeleria con ID " + p.getId() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }

        for (ProductoGeek g : geekRepo.findAll()) {
            try {
                items.add(new ItemCatalogo(
                        g.getId(),
                        g.getNombre(),
                        g.getCategoria(),
                        "Geek",
                        g.getPrecio(),
                        "geek/" + g.getImagen()
                ));
            } catch (Exception e) {
                System.err.println("Error al procesar ProductoGeek con ID " + g.getId() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }

        return items;
    }

    // Este método busca un producto específico por tipo (ej. "libro") e id.
    // Devuelve un ItemCatalogo con la info del producto, o null si no lo encuentra.
    public ItemCatalogo obtenerItem(String tipo, Long id) {
        // Si tipo o id son null, devuelve null.
        if (tipo == null || id == null) return null;
        // Usa un switch para decidir qué repositorio usar según el tipo.
        switch (tipo.toLowerCase()) {
            case "libro":
                // Busca el libro por id y lo convierte a ItemCatalogo.
                return libroRepo.findById(id)
                        .map(l -> new ItemCatalogo(l.getId(), l.getTitulo(), l.getCategoria(), "Libro", l.getPrecio(), "portadas/" + l.getPortada()))
                        .orElse(null);
            case "cómic":
            case "comic":
                return comicRepo.findById(id)
                        .map(c -> new ItemCatalogo(c.getId(), c.getTitulo(), c.getCategoria(), "Cómic", c.getPrecio(), "portadas/" + c.getPortada()))
                        .orElse(null);
            case "papelería":
            case "papeleria":
                return papeleriaRepo.findById(id)
                        .map(p -> new ItemCatalogo(p.getId(), p.getNombre(), p.getCategoria(), "Papelería", p.getPrecio(), "papeleria/" + p.getImagen()))
                        .orElse(null);
            case "geek":
                return geekRepo.findById(id)
                        .map(g -> new ItemCatalogo(g.getId(), g.getNombre(), g.getCategoria(), "Geek", g.getPrecio(), "geek/" + g.getImagen()))
                        .orElse(null);
            default:
                return null;
        }
    }
}
