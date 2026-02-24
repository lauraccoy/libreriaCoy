package com.libreriacoy.repository;

/*
 * Archivo: LibroRepository.java
 * Paquete: com.libreriacoy.repository
 * Propósito: Repositorio Spring Data JPA: acceso a datos para la entidad correspondiente.
 * Nota: Comentarios añadidos para documentación y mantenimiento (2026-01-23).
 */


import org.springframework.data.jpa.repository.JpaRepository;
import com.libreriacoy.model.Libro;

// Esta interfaz es un repositorio para manejar los libros en la base de datos.
// Extiende JpaRepository, que proporciona métodos listos para guardar, buscar y eliminar libros.
// No necesitamos escribir código aquí; Spring lo hace automáticamente.
/**
 * LibroRepository.
 *
 * Repositorio Spring Data JPA: acceso a datos para la entidad correspondiente.
 */
public interface LibroRepository extends JpaRepository<Libro, Long> {
}

