package com.libreriacoy.dto;

/*
 * Archivo: ItemCatalogo.java
 * Paquete: com.libreriacoy.dto
 * Propósito: DTO (Data Transfer Object): estructura ligera para transportar datos entre capas/vistas.
 * Nota: Comentarios añadidos para documentación y mantenimiento (2026-01-23).
 */


// Esta clase es un DTO (Data Transfer Object) que representa un item del catálogo.
// Un DTO es un objeto simple para pasar datos entre partes de la aplicación.
// Contiene info básica de cualquier producto: id, nombre, etc.
/**
 * ItemCatalogo.
 *
 * DTO (Data Transfer Object): estructura ligera para transportar datos entre capas/vistas.
 */
public class ItemCatalogo {

    // El id único del producto.
    private Long id;
    // El nombre del producto.
    private String nombre;
    // La categoría del producto.
    private String categoria;
    // El tipo de producto (ej. "Libro", "Cómic").
    private String tipoProducto;
    // El precio del producto.
    private Double precio;

    // La ruta de la imagen del producto (relativa o absoluta).
    private String imagen;

    // Constructor vacío.
    public ItemCatalogo() {}

    // Constructor con parámetros para crear un ItemCatalogo.
    public ItemCatalogo(Long id, String nombre, String categoria, String tipoProducto, Double precio, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.tipoProducto = tipoProducto;
        this.precio = precio;
        this.imagen = normalizarRutaWeb(imagen);
    }

    // Método privado para normalizar la ruta de la imagen para la web.
    private String normalizarRutaWeb(String ruta) {
        if (ruta == null) return null;

        String r = ruta.trim();
        if (r.isEmpty()) return null;

        // Windows -> Web
        r = r.replace("\\", "/");

        // Si viene absoluta, recortamos desde /static/ o src/main/resources/static/
        String marker1 = "/static/";
        int i1 = r.indexOf(marker1);
        if (i1 >= 0) r = r.substring(i1 + marker1.length());

        String marker3 = "src/main/resources/static/";
        int i3 = r.indexOf(marker3);
        if (i3 >= 0) r = r.substring(i3 + marker3.length());

        String marker2 = "static/";
        if (r.startsWith(marker2)) r = r.substring(marker2.length());

        // Quitamos "/" inicial
        while (r.startsWith("/")) r = r.substring(1);

        return r.isEmpty() ? null : r;
    }

    /**
     * Devuelve la URL final para usar en <img src="...">
     * - Si es http(s), la deja tal cual
     * - Si es relativa ("portadas/x.jpg"), la convierte en "/portadas/x.jpg"
     * - Si es null, pone una imagen placeholder
     */
    public String getImagenUrl() {
        if (imagen == null || imagen.isBlank()) return "/img/no-image.png";
        String r = imagen.trim();
        if (r.startsWith("http://") || r.startsWith("https://")) return r;
        if (r.startsWith("/")) return r;
        return "/" + r;
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getTipoProducto() { return tipoProducto; }
    public void setTipoProducto(String tipoProducto) { this.tipoProducto = tipoProducto; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = normalizarRutaWeb(imagen); }
    public String getImagenPath() {
        if (imagen == null || imagen.isBlank()) return null;

        // Normaliza tipoProducto
        String tipo = (tipoProducto == null) ? "" : tipoProducto.toLowerCase();

        // Ajusta nombres EXACTOS a los que uses tú: "LIBRO", "COMIC", "PAPELERIA", "GEEK"
        if (tipo.contains("geek")) return "/geek/" + imagen;
        if (tipo.contains("papeler")) return "/papeleria/" + imagen;

        // Libros y cómics los metes en /portadas
        return "/portadas/" + imagen;
    }

}
