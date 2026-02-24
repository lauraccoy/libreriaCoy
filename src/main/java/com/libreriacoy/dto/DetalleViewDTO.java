package com.libreriacoy.dto;

/*
 * Archivo: DetalleViewDTO.java
 * Paquete: com.libreriacoy.dto
 * Propósito: DTO (Data Transfer Object): estructura ligera para transportar datos entre capas/vistas.
 * Nota: Comentarios añadidos para documentación y mantenimiento (2026-01-23).
 */


/**
 * DetalleViewDTO.
 *
 * DTO (Data Transfer Object): estructura ligera para transportar datos entre capas/vistas.
 */
public class DetalleViewDTO {

    private String tipo;      // "Libro", "Comic", "Papelería", "Geek"
    private String img;       // ruta imagen (ej: "portadas/ElSegundoSexo.jpg")

    // Campos comunes
    private String titulo;    // (Libro/Comic) o nombre (Papelería/Geek)
    private String categoria;
    private Double precio;

    // Campos específicos
    private String autor;       // Libro/Comic
    private String editorial;   // Comic
    private String isbn;        // Libro/Comic

    private String marca;       // Papelería
    private String franquicia;  // Geek
    private Integer stock;      // Geek

    public DetalleViewDTO() {}

    // ===== Getters/Setters =====
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getFranquicia() { return franquicia; }
    public void setFranquicia(String franquicia) { this.franquicia = franquicia; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
