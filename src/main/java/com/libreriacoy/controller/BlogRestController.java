package com.libreriacoy.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.libreriacoy.dto.BlogPostDto;
import com.libreriacoy.model.BlogPost;
import com.libreriacoy.repository.BlogPostRepository;
import com.libreriacoy.service.BlogContentService;

@RestController
@RequestMapping("/api/blog")
public class BlogRestController {

    private final BlogPostRepository blogRepo;
    private final BlogContentService contentService;

    public BlogRestController(BlogPostRepository blogRepo, BlogContentService contentService) {
        this.blogRepo = blogRepo;
        this.contentService = contentService;
    }

    // ✅ LISTA (sin contenido pesado)
    @GetMapping
    public List<BlogPostDto> list() {
        return blogRepo.findAllByPublicadoTrueOrderByCreatedAtDesc()
                .stream()
                .map(this::toDtoSummary)
                .toList();
    }

    // ✅ DETALLE (con contenido leído del archivo)
    @GetMapping("/{slug}")
    public BlogPostDto detail(@PathVariable String slug) {
        BlogPost post = blogRepo.findBySlugAndPublicadoTrue(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post no encontrado"));

        BlogPostDto dto = toDtoSummary(post);
        dto.contenido = contentService.loadContent(post.getContentPath());
        return dto;
    }

    private BlogPostDto toDtoSummary(BlogPost p) {
        BlogPostDto dto = new BlogPostDto();
        dto.id = p.getId();
        dto.titulo = p.getTitulo();
        dto.slug = p.getSlug();
        dto.resumen = p.getResumen();
        dto.imagen = p.getImagen();
        dto.publicado = p.isPublicado();
        dto.createdAt = p.getCreatedAt();
        return dto;
    }
}
