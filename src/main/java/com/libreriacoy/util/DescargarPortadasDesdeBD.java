package com.libreriacoy.util;

/*
 * Archivo: DescargarPortadasDesdeBD.java
 * Paquete: com.libreriacoy.util
 * Propósito: Utilidad/Script: tareas auxiliares (p.ej. comprobar o descargar imágenes).
 * Nota: Comentarios añadidos para documentación y mantenimiento (2026-01-23).
 */


import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * DescargarPortadasDesdeBD.
 *
 * Utilidad/Script: tareas auxiliares (p.ej. comprobar o descargar imágenes).
 */
public class DescargarPortadasDesdeBD {

    // ====== CONFIGURACIÓN BD (AJÚSTALA A TU PROYECTO) ======
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/libreriacoy?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "060304";

    // ====== CARPETA DESTINO (Spring Boot sirve /static como recursos) ======
    private static final Path DEST = Paths.get("src/main/resources/static/portadas");

    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(15))
            .build();

    // Cambia a false si NO quieres que toque la BD (solo descarga archivos)
    private static final boolean ACTUALIZAR_COLUMNA_PORTADA_EN_BD = true;

    public static void main(String[] args) throws Exception {
        Files.createDirectories(DEST);

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {

            System.out.println("Conectado a BD ✅");
            int ok = 0, fail = 0, skip = 0;

            // 1) LIBROS
            List<Item> libros = cargarLibros(conn);
            System.out.println("Libros detectados: " + libros.size());

            Resultado rLibros = procesarLista(conn, libros, "libros");
            ok += rLibros.ok; fail += rLibros.fail; skip += rLibros.skip;

            // 2) COMICS
            List<Item> comics = cargarComics(conn);
            System.out.println("Comics detectados: " + comics.size());

            Resultado rComics = procesarLista(conn, comics, "comics");
            ok += rComics.ok; fail += rComics.fail; skip += rComics.skip;

            System.out.println("\n==============================");
            System.out.println("RESUMEN FINAL");
            System.out.println("OK   : " + ok);
            System.out.println("SKIP : " + skip);
            System.out.println("FAIL : " + fail);
            System.out.println("Guardadas en: " + DEST.toAbsolutePath());
            System.out.println("==============================\n");
        }
    }

    // ===================== LECTURA DESDE BD =====================

    private static List<Item> cargarLibros(Connection conn) throws SQLException {
        String sql = "SELECT id, titulo, autor, portada FROM libros";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Item> out = new ArrayList<>();
            while (rs.next()) {
                out.add(new Item(
                        "libros",
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("portada")
                ));
            }
            return out;
        }
    }

    private static List<Item> cargarComics(Connection conn) throws SQLException {
        String sql = "SELECT id, titulo, autor, portada FROM comics";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Item> out = new ArrayList<>();
            while (rs.next()) {
                out.add(new Item(
                        "comics",
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("portada")
                ));
            }
            return out;
        }
    }

    // ===================== PROCESO PRINCIPAL =====================

    private static Resultado procesarLista(Connection conn, List<Item> items, String tabla) throws SQLException, InterruptedException {
        int ok = 0, fail = 0, skip = 0;

        for (Item it : items) {
            String fileName = decidirNombreArchivo(it);
            Path outFile = DEST.resolve(fileName);

            if (Files.exists(outFile) && sizeSafe(outFile) > 0) {
                System.out.println("[SKIP] " + tabla + " #" + it.id + " -> " + it.titulo + " (ya existe)");
                skip++;

                if (ACTUALIZAR_COLUMNA_PORTADA_EN_BD) {
                    actualizarPortada(conn, tabla, it.id, "portadas/" + fileName);
                }
                continue;
            }

            // 1) Google Books
            String urlImg = null;
            try {
                urlImg = googleBooksThumbnail(it.titulo, it.autor);
            } catch (IOException e) {
                // ignore -> probar plan B
            }

            // 2) Plan B: Open Library
            if (urlImg == null) {
                try {
                    urlImg = openLibraryCoverBySearch(it.titulo, it.autor);
                } catch (IOException e) {
                    // ignore
                }
            }

            if (urlImg != null && descargar(urlImg, outFile)) {
                System.out.println("[OK]   " + tabla + " #" + it.id + " -> " + it.titulo + " (" + fileName + ")");
                ok++;

                if (ACTUALIZAR_COLUMNA_PORTADA_EN_BD) {
                    actualizarPortada(conn, tabla, it.id, "portadas/" + fileName);
                }
            } else {
                System.out.println("[FAIL] " + tabla + " #" + it.id + " -> " + it.titulo + " (no encontrada)");
                fail++;
            }

            Thread.sleep(250);
        }

        return new Resultado(ok, fail, skip);
    }

    private static void actualizarPortada(Connection conn, String tabla, int id, String nuevaRuta) throws SQLException {
        String sql = "UPDATE " + tabla + " SET portada = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuevaRuta);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    // ===================== BÚSQUEDA DE IMÁGENES =====================

    private static String googleBooksThumbnail(String title, String author) throws IOException, InterruptedException {
        String q = "intitle:" + normalizeForQuery(title) + " inauthor:" + normalizeForQuery(author);
        String url = "https://www.googleapis.com/books/v1/volumes?q=" +
                URLEncoder.encode(q, StandardCharsets.UTF_8) +
                "&maxResults=1";

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(25))
                .GET()
                .build();

        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() != 200) return null;

        String body = res.body();

        String thumb = extractJsonStringValue(body, "\"thumbnail\"");
        if (thumb == null) thumb = extractJsonStringValue(body, "\"smallThumbnail\"");
        if (thumb == null) return null;

        if (thumb.startsWith("http://")) thumb = "https://" + thumb.substring(7);
        thumb = thumb.replace("&zoom=1", "&zoom=2");

        return thumb;
    }

    private static String openLibraryCoverBySearch(String title, String author) throws IOException, InterruptedException {
        String base = "https://openlibrary.org/search.json";
        String params = "title=" + URLEncoder.encode(title, StandardCharsets.UTF_8) +
                "&author=" + URLEncoder.encode(author, StandardCharsets.UTF_8) +
                "&limit=1";

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(base + "?" + params))
                .timeout(Duration.ofSeconds(25))
                .GET()
                .build();

        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() != 200) return null;

        String body = res.body();

        Integer coverId = extractJsonIntValue(body, "\"cover_i\"");
        if (coverId == null) return null;

        return "https://covers.openlibrary.org/b/id/" + coverId + "-L.jpg";
    }

    // ===================== DESCARGA =====================

    private static boolean descargar(String url, Path outFile) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(30))
                    .GET()
                    .build();

            HttpResponse<byte[]> res = client.send(req, HttpResponse.BodyHandlers.ofByteArray());
            if (res.statusCode() != 200 || res.body() == null || res.body().length == 0) return false;

            Files.write(outFile, res.body(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ===================== UTILIDADES =====================

    private static long sizeSafe(Path p) {
        try { return Files.size(p); } catch (Exception e) { return 0; }
    }

    private static String decidirNombreArchivo(Item it) {
        if (it.portada != null && !it.portada.isBlank()) {
            String raw = it.portada.replace("\\", "/");
            int idx = raw.lastIndexOf("/");
            String name = (idx >= 0) ? raw.substring(idx + 1) : raw;
            return safeFileName(name);
        }
        return safeFileName(it.titulo.replace(" ", "") + ".jpg");
    }

    private static String safeFileName(String s) {
        return s.replaceAll("[^a-zA-Z0-9._-]", "");
    }

    private static String normalizeForQuery(String s) {
        if (s == null) return "";
        return s.trim();
    }

    private static String extractJsonStringValue(String json, String key) {
        int k = json.indexOf(key);
        if (k < 0) return null;

        int colon = json.indexOf(":", k);
        if (colon < 0) return null;

        int firstQuote = json.indexOf("\"", colon + 1);
        if (firstQuote < 0) return null;

        int secondQuote = json.indexOf("\"", firstQuote + 1);
        if (secondQuote < 0) return null;

        return json.substring(firstQuote + 1, secondQuote).replace("\\/", "/");
    }

    private static Integer extractJsonIntValue(String json, String key) {
        int k = json.indexOf(key);
        if (k < 0) return null;

        int colon = json.indexOf(":", k);
        if (colon < 0) return null;

        int i = colon + 1;
        while (i < json.length() && Character.isWhitespace(json.charAt(i))) i++;

        int j = i;
        while (j < json.length() && Character.isDigit(json.charAt(j))) j++;

        if (j == i) return null;

        try {
            return Integer.parseInt(json.substring(i, j));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // ===================== MODELOS (sin record) =====================

    private static class Item {
        final String tabla;
        final int id;
        final String titulo;
        final String autor;
        final String portada;

        Item(String tabla, int id, String titulo, String autor, String portada) {
            this.tabla = tabla;
            this.id = id;
            this.titulo = titulo;
            this.autor = autor;
            this.portada = portada;
        }
    }

    private static class Resultado {
        final int ok;
        final int fail;
        final int skip;

        Resultado(int ok, int fail, int skip) {
            this.ok = ok;
            this.fail = fail;
            this.skip = skip;
        }
    }
}
