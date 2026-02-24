package com.libreriacoy.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

@Service
public class BlogContentService {

    public String loadContent(String contentPath) {
        try {
            var res = new ClassPathResource("static/blog/" + contentPath);
            if (!res.exists()) return "<p>Contenido no disponible.</p>";

            try (var in = res.getInputStream()) {
                return StreamUtils.copyToString(in, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            return "<p>Error cargando contenido.</p>";
        }
    }
}
