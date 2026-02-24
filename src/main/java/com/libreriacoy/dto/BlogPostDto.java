package com.libreriacoy.dto;

import java.time.LocalDateTime;

public class BlogPostDto {
    public Long id;
    public String titulo;
    public String slug;
    public String resumen;
    public String contenido; // ✅ solo en detalle
    public String imagen;
    public boolean publicado;
    public LocalDateTime createdAt;
}
