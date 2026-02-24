package com.libreriacoy;

/*
 * Archivo: LibreriaCoyApplication.java
 * Paquete: com.libreriacoy
 * Propósito: Clase de arranque de Spring Boot.
 * Nota: Comentarios añadidos para documentación y mantenimiento (2026-01-23).
 */


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Esta anotación indica que esta clase es la principal de una aplicación Spring Boot.
// Spring Boot configura automáticamente muchas cosas, como el servidor web y la base de datos.
@SpringBootApplication
/**
 * LibreriaCoyApplication.
 *
 * Clase de arranque de Spring Boot.
 */
public class LibreriaCoyApplication {

    // El método main es el punto de entrada de cualquier programa Java.
    // Aquí se inicia la aplicación Spring Boot.
	public static void main(String[] args) {
		SpringApplication.run(LibreriaCoyApplication.class, args);
	}
}
