class BlogPost {
  final int? id;
  final String titulo;
  final String slug;
  final String? resumen;
  final String contenido;
  final String? imagen;
  final String? createdAt;

  BlogPost({
    required this.id,
    required this.titulo,
    required this.slug,
    required this.resumen,
    required this.contenido,
    required this.imagen,
    required this.createdAt,
  });

  factory BlogPost.fromJson(Map<String, dynamic> json) {
    return BlogPost(
      id: json['id'] is int ? json['id'] : int.tryParse('${json['id']}'),
      titulo: (json['titulo'] ?? '').toString(),
      slug: (json['slug'] ?? '').toString(),
      resumen: json['resumen']?.toString(),
      contenido: (json['contenido'] ?? '').toString(),
      imagen: json['imagen']?.toString(),
      createdAt: json['createdAt']?.toString() ?? json['created_at']?.toString(),
    );
  }

  String get contenidoPlano {
    final noTags = contenido.replaceAll(RegExp(r'<[^>]*>'), ' ');
    return noTags.replaceAll(RegExp(r'\s+'), ' ').trim();
  }
}
