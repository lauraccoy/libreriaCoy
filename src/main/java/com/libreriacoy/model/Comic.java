package com.libreriacoy.model;

/*
 * Archivo: Comic.java
 * Paquete: com.libreriacoy.model
 * Propósito: Entidad JPA: mapea una tabla de base de datos a un objeto Java.
 * Nota: Comentarios añadidos para documentación y mantenimiento (2026-01-23).
 */


import jakarta.persistence.*;

@Entity
@Table(name = "comics")
/**
 * Comic.
 *
 * Entidad JPA: mapea una tabla de base de datos a un objeto Java.
 */
public class Comic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String autor;
    private String editorial;
    private String categoria;
    private String isbn;
    private String tipo; // Nuevo / Segunda Mano
    private double precio;
    private String portada;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getEditorial() {
		return editorial;
	}
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public String getPortada() {
		return portada;
	}
	public void setPortada(String portada) {
		this.portada = portada;
	}

    
}
