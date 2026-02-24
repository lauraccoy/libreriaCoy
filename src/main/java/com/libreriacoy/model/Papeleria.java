package com.libreriacoy.model;

/*
 * Archivo: Papeleria.java
 * Paquete: com.libreriacoy.model
 * Propósito: Entidad JPA: mapea una tabla de base de datos a un objeto Java.
 * Nota: Comentarios añadidos para documentación y mantenimiento (2026-01-23).
 */


import jakarta.persistence.*;

@Entity
@Table(name = "papeleria")
/**
 * Papeleria.
 *
 * Entidad JPA: mapea una tabla de base de datos a un objeto Java.
 */
public class Papeleria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String categoria;
    private String marca;
    private String tipo; // Económica / Estándar / Premium
    private double precio;
    private String imagen;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
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
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

    
}
