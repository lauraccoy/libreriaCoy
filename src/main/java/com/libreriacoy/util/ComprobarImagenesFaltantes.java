package com.libreriacoy.util;

/*
 * Archivo: ComprobarImagenesFaltantes.java
 * Paquete: com.libreriacoy.util
 * Propósito: Utilidad/Script: tareas auxiliares (p.ej. comprobar o descargar imágenes).
 * Nota: Comentarios añadidos para documentación y mantenimiento (2026-01-23).
 */


import java.nio.file.*;
import java.sql.*;
import java.util.*;

/**
 * ComprobarImagenesFaltantes.
 *
 * Utilidad/Script: tareas auxiliares (p.ej. comprobar o descargar imágenes).
 */
public class ComprobarImagenesFaltantes {

    // === Ajusta igual que en application.properties ===
    private static final String JDBC_URL  = "jdbc:mysql://localhost:3306/libreriacoy?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "060304";

    // === Carpetas donde deberían existir ===
    private static final Path STATIC_DIR   = Paths.get("src/main/resources/static");
    private static final Path PORTADAS_DIR = STATIC_DIR.resolve("portadas");
    private static final Path PAPEL_DIR    = STATIC_DIR.resolve("papeleria");
    private static final Path GEEK_DIR     = STATIC_DIR.resolve("geek");

    public static void main(String[] args) throws Exception {

        System.out.println("📁 static: " + STATIC_DIR.toAbsolutePath());
        Files.createDirectories(PORTADAS_DIR);
        Files.createDirectories(PAPEL_DIR);
        Files.createDirectories(GEEK_DIR);

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {

            // 1) Leer rutas desde BD
            Set<String> rutasPortadas = leerRutas(conn,
                    "SELECT portada FROM libros WHERE portada IS NOT NULL AND portada <> ''",
                    "SELECT portada FROM comics WHERE portada IS NOT NULL AND portada <> ''"
            );

            Set<String> rutasPapeleria = leerRutas(conn,
                    "SELECT imagen FROM papeleria WHERE imagen IS NOT NULL AND imagen <> ''"
            );

            Set<String> rutasGeek = leerRutas(conn,
                    "SELECT imagen FROM productos_geek WHERE imagen IS NOT NULL AND imagen <> ''"
            );

            // 2) Comprobar archivos existentes
            List<String> faltanPortadas = comprobarFaltantes(rutasPortadas, STATIC_DIR);
            List<String> faltanPapeleria = comprobarFaltantes(rutasPapeleria, STATIC_DIR);
            List<String> faltanGeek = comprobarFaltantes(rutasGeek, STATIC_DIR);

            // 3) Mostrar resultados
            imprimir("📚 PORTADAS (libros+comics)", faltanPortadas);
            imprimir("✏️ PAPELERIA", faltanPapeleria);
            imprimir("🎮 GEEK", faltanGeek);

            int total = faltanPortadas.size() + faltanPapeleria.size() + faltanGeek.size();
            System.out.println("\n✅ TOTAL FALTANTES: " + total);
        }
    }

    private static Set<String> leerRutas(Connection conn, String... queries) throws SQLException {
        Set<String> rutas = new LinkedHashSet<>();
        for (String q : queries) {
            try (PreparedStatement ps = conn.prepareStatement(q);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String ruta = rs.getString(1);
                    if (ruta != null) {
                        ruta = ruta.replace("\\", "/").trim();
                        if (!ruta.isEmpty()) rutas.add(ruta);
                    }
                }
            }
        }
        return rutas;
    }

    /**
     * STATIC_DIR = .../static
     * ruta BD = "portadas/Algo.jpg" o "papeleria/X.jpg"
     * comprueba si existe static/rutaBD
     */
    private static List<String> comprobarFaltantes(Set<String> rutasBD, Path staticDir) {
        List<String> faltan = new ArrayList<>();
        for (String ruta : rutasBD) {
            Path p = staticDir.resolve(ruta);
            if (!Files.exists(p) || sizeSafe(p) == 0) {
                faltan.add(ruta);
            }
        }
        return faltan;
    }

    private static long sizeSafe(Path p) {
        try { return Files.size(p); } catch (Exception e) { return 0; }
    }

    private static void imprimir(String titulo, List<String> faltan) {
        System.out.println("\n==============================");
        System.out.println(titulo);
        System.out.println("Faltan: " + faltan.size());
        for (String f : faltan) System.out.println(" - " + f);
        System.out.println("==============================");
    }
}
