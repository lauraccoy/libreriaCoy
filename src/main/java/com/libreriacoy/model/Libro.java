package com.libreriacoy.model;

/*
 * Archivo: Libro.java
 * Paquete: com.libreriacoy.model
 * Propósito: Entidad JPA: mapea una tabla de base de datos a un objeto Java.
 * Nota: Comentarios añadidos para documentación y mantenimiento (2026-01-23).
 */


import jakarta.persistence.*;

// Esta clase representa un libro en la base de datos. Es un "modelo" de datos.
// @Entity indica que esta clase se guarda en la base de datos.
// @Table especifica el nombre de la tabla en la base de datos.
@Entity
@Table(name = "libros")
/**
 * Libro.
 *
 * Entidad JPA: mapea una tabla de base de datos a un objeto Java.
 */
public class Libro {

    // El id es el identificador único de cada libro. Se genera automáticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // El título del libro.
    private String titulo;
    // El autor del libro.
    private String autor;
    // La categoría del libro (ej. "Ficción", "Ciencia").
    private String categoria;
    // El ISBN es un código único para identificar el libro.
    private String isbn;
    // El tipo de libro (ej. "Físico", "Digital").
    private String tipo;
    // El precio del libro.
    private double precio;
    // La ruta de la imagen de la portada.
    private String portada;

    // Métodos para obtener (get) y cambiar (set) el id.
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // Métodos para obtener y cambiar el título.
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    // Métodos para obtener y cambiar el autor.
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    // Métodos para obtener y cambiar la categoría.
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    // Métodos para obtener y cambiar el ISBN.
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    // Métodos para obtener y cambiar el tipo.
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    // Métodos para obtener y cambiar el precio.
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    // Métodos para obtener y cambiar la portada.
    public String getPortada() { return portada; }
    public void setPortada(String portada) { this.portada = portada; }
}
