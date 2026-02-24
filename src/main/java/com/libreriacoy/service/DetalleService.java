package com.libreriacoy.service;

/*
 * Archivo: DetalleService.java
 * Paquete: com.libreriacoy.service
 */

import org.springframework.stereotype.Service;

import com.libreriacoy.dto.DetalleViewDTO;
import com.libreriacoy.model.Comic;
import com.libreriacoy.model.Libro;
import com.libreriacoy.model.Papeleria;
import com.libreriacoy.model.ProductoGeek;
import com.libreriacoy.repository.ComicRepository;
import com.libreriacoy.repository.LibroRepository;
import com.libreriacoy.repository.PapeleriaRepository;
import com.libreriacoy.repository.ProductoGeekRepository;

@Service
public class DetalleService {

    private final LibroRepository libroRepository;
    private final ComicRepository comicRepository;
    private final PapeleriaRepository papeleriaRepository;
    private final ProductoGeekRepository productoGeekRepository;

    public DetalleService(LibroRepository libroRepository,
                          ComicRepository comicRepository,
                          PapeleriaRepository papeleriaRepository,
                          ProductoGeekRepository productoGeekRepository) {
        this.libroRepository = libroRepository;
        this.comicRepository = comicRepository;
        this.papeleriaRepository = papeleriaRepository;
        this.productoGeekRepository = productoGeekRepository;
    }

    public DetalleViewDTO obtenerDetalle(String tipo, Long id) {

        String tipoNorm = normalizarTipo(tipo);

        DetalleViewDTO dto = new DetalleViewDTO();
        dto.setTipo(tipoNorm);

        switch (tipoNorm) {

            case "LIBRO": {
                Libro l = libroRepository.findById(id).orElse(null);
                if (l == null) return null;

                dto.setImg(normalizarRutaImagen("LIBRO", l.getPortada()));
                dto.setTitulo(l.getTitulo());
                dto.setAutor(l.getAutor());
                dto.setCategoria(l.getCategoria());
                dto.setIsbn(l.getIsbn());
                dto.setPrecio(l.getPrecio());
                return dto;
            }

            case "COMIC": {
                Comic c = comicRepository.findById(id).orElse(null);
                if (c == null) return null;

                dto.setImg(normalizarRutaImagen("COMIC", c.getPortada()));
                dto.setTitulo(c.getTitulo());
                dto.setAutor(c.getAutor());
                dto.setEditorial(c.getEditorial());
                dto.setCategoria(c.getCategoria());
                dto.setIsbn(c.getIsbn());
                dto.setPrecio(c.getPrecio());
                return dto;
            }

            case "PAPELERIA": {
                Papeleria p = papeleriaRepository.findById(id).orElse(null);
                if (p == null) return null;

                dto.setImg(normalizarRutaImagen("PAPELERIA", p.getImagen()));
                dto.setTitulo(p.getNombre());
                dto.setMarca(p.getMarca());
                dto.setCategoria(p.getCategoria());
                dto.setPrecio(p.getPrecio());
                return dto;
            }

            case "GEEK": {
                ProductoGeek g = productoGeekRepository.findById(id).orElse(null);
                if (g == null) return null;

                dto.setImg(normalizarRutaImagen("GEEK", g.getImagen()));
                dto.setTitulo(g.getNombre());
                dto.setFranquicia(g.getFranquicia());
                dto.setCategoria(g.getCategoria());
                dto.setStock(g.getStock());
                dto.setPrecio(g.getPrecio());
                return dto;
            }

            default:
                return null;
        }
    }

    private String normalizarTipo(String tipo) {
        if (tipo == null) return "";
        String t = tipo.trim().toUpperCase();

        // quitar tildes
        t = t.replace("Í", "I").replace("Á", "A").replace("É", "E").replace("Ó", "O").replace("Ú", "U");

        return switch (t) {
            case "LIBRO", "LIBROS" -> "LIBRO";
            case "COMIC", "CÓMIC", "COMICS" -> "COMIC";
            case "PAPELERIA", "PAPELERÍA" -> "PAPELERIA";
            case "GEEK" -> "GEEK";
            default -> t;
        };
    }

    /**
     * Devuelve una ruta usable en HTML:
     *  - Si es URL http(s) => se deja tal cual
     *  - Si es "portadas/x.jpg" o "geek/x.png" => se deja tal cual
     *  - Si es solo "x.jpg" => se le antepone la carpeta según tipo
     */
    private String normalizarRutaImagen(String tipo, String valorBD) {
        if (valorBD == null || valorBD.isBlank()) return null;

        String v = valorBD.trim();

        if (v.startsWith("http://") || v.startsWith("https://")) return v;

        // Quitar "/" inicial
        if (v.startsWith("/")) v = v.substring(1);

        // Si ya viene con carpeta, lo usamos tal cual
        if (v.contains("/")) return v;

        return switch (tipo) {
            case "LIBRO", "COMIC" -> "portadas/" + v;
            case "PAPELERIA" -> "papeleria/" + v;
            case "GEEK" -> "geek/" + v;
            default -> v;
        };
    }
}
