// Modelo Dart para mapear la respuesta de /api/detalle

class DetalleView {
  final String tipo; // Ej: "Libro", "Comic", "Papelería", "Geek"
  final String? img; // URL absoluta de la imagen (puede ser null)
  final String? titulo;
  final String? categoria;
  final double? precio;

  // Campos específicos según tipo de producto
  final String? autor;      // Libro/Comic
  final String? editorial;  // Comic
  final String? isbn;       // Libro/Comic

  final String? marca;      // Papelería
  final String? franquicia; // Geek
  final int? stock;         // Geek

  // Constructor con todos los campos (inmutables desde fuera)
  DetalleView({
    required this.tipo,
    this.img,
    this.titulo,
    this.categoria,
    this.precio,
    this.autor,
    this.editorial,
    this.isbn,
    this.marca,
    this.franquicia,
    this.stock,
  });

  // Factory para crear el modelo a partir de JSON (respuesta del servidor)
  factory DetalleView.fromJson(Map<String, dynamic> json) {
    return DetalleView(
      tipo: json['tipo'] ?? '',
      img: json['img'],
      titulo: json['titulo'],
      categoria: json['categoria'],
      precio: json['precio'] == null ? null : (json['precio'] as num).toDouble(),
      autor: json['autor'],
      editorial: json['editorial'],
      isbn: json['isbn'],
      marca: json['marca'],
      franquicia: json['franquicia'],
      stock: json['stock'] == null ? null : (json['stock'] as num).toInt(),
    );
  }

  // Método auxiliar: devuelve una URL válida para imagen o una ruta placeholder si es null
  String imagenOrPlaceholder() {
    if (img == null || img!.trim().isEmpty) return '/img/no-image.png';
    return img!;
  }
}
