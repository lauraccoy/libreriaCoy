class ItemCatalogo {
  final int id;
  final String nombre;
  final String categoria;
  final String tipoProducto;
  final double precio;
  final String imagen; // URL absoluta

  ItemCatalogo({
    required this.id,
    required this.nombre,
    required this.categoria,
    required this.tipoProducto,
    required this.precio,
    required this.imagen,
  });

  factory ItemCatalogo.fromJson(Map<String, dynamic> json) {
    return ItemCatalogo(
      id: (json['id'] as num).toInt(),
      nombre: json['nombre'] ?? '',
      categoria: json['categoria'] ?? '',
      tipoProducto: json['tipoProducto'] ?? '',
      precio: (json['precio'] == null) ? 0.0 : (json['precio'] as num).toDouble(),
      imagen: json['imagen'] ?? '',
    );
  }
}
