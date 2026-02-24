package com.libreriacoy.repository;

import com.libreriacoy.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    List<BlogPost> findAllByPublicadoTrueOrderByCreatedAtDesc();

    Optional<BlogPost> findBySlugAndPublicadoTrue(String slug);
}
