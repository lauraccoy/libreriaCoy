package com.libreriacoy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.libreriacoy.repository.BlogPostRepository;
import com.libreriacoy.service.BlogContentService;

@Controller
public class BlogController {

    private final BlogPostRepository blogRepo;
    private final BlogContentService contentService;

    public BlogController(BlogPostRepository blogRepo, BlogContentService contentService) {
        this.blogRepo = blogRepo;
        this.contentService = contentService;
    }

    @GetMapping("/blog")
    public String blog(Model model) {
        model.addAttribute("titulo", "Blog | Librería Coy");
        model.addAttribute("contenido", "blog");
        model.addAttribute("posts", blogRepo.findAllByPublicadoTrueOrderByCreatedAtDesc());
        return "layout";
    }

    @GetMapping("/blog/{slug}")
    public String post(@PathVariable String slug, Model model) {
        var post = blogRepo.findBySlugAndPublicadoTrue(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // ✅ CLAVE: cargar el HTML desde /static/blog/{content_path}
        String html = contentService.loadContent(post.getContentPath());
        post.setContenido(html);

        // (opcional) Log para comprobar que carga contenido
        System.out.println("BLOG HTML LEN (" + slug + ") = " + (html == null ? 0 : html.length()));

        model.addAttribute("titulo", post.getTitulo() + " | Blog");
        model.addAttribute("contenido", "blog_detalle");
        model.addAttribute("post", post);
        return "layout";
    }
}
