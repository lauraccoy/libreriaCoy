package com.libreriacoy.repository;

/*
 * Archivo: ComicRepository.java
 * Paquete: com.libreriacoy.repository
 * Propósito: Repositorio Spring Data JPA: acceso a datos para la entidad correspondiente.
 * Nota: Comentarios añadidos para documentación y mantenimiento (2026-01-23).
 */


import org.springframework.data.jpa.repository.JpaRepository;
import com.libreriacoy.model.Comic;

/**
 * ComicRepository.
 *
 * Repositorio Spring Data JPA: acceso a datos para la entidad correspondiente.
 */
public interface ComicRepository extends JpaRepository<Comic, Long> {
}
