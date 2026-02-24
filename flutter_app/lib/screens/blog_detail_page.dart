import 'package:flutter/material.dart';
import '../services/api_service.dart';
import '../models/blog_post.dart';

class BlogDetailPage extends StatefulWidget {
  final String slug;
  const BlogDetailPage({super.key, required this.slug});

  @override
  State<BlogDetailPage> createState() => _BlogDetailPageState();
}

class _BlogDetailPageState extends State<BlogDetailPage> {
  late Future<BlogPost> _future;

  @override
  void initState() {
    super.initState();
    _future = ApiService.fetchBlogPostBySlug(widget.slug);
  }

  Future<void> _reload() async {
    setState(() {
      _future = ApiService.fetchBlogPostBySlug(widget.slug);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: FutureBuilder<BlogPost>(
        future: _future,
        builder: (context, snap) {
          if (snap.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }
          if (snap.hasError) {
            return Center(
              child: Padding(
                padding: const EdgeInsets.all(16),
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    const Icon(Icons.error_outline, size: 48),
                    const SizedBox(height: 12),
                    Text(
                      'No se pudo cargar el artículo.\n\n${snap.error}',
                      textAlign: TextAlign.center,
                    ),
                    const SizedBox(height: 12),
                    FilledButton(
                      onPressed: _reload,
                      child: const Text('Reintentar'),
                    ),
                  ],
                ),
              ),
            );
          }

          final post = snap.data!;
          return SingleChildScrollView(
            padding: const EdgeInsets.all(16),
            child: Card(
              child: Padding(
                padding: const EdgeInsets.all(16),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      post.titulo,
                      style: const TextStyle(
                        fontSize: 20,
                        fontWeight: FontWeight.w800,
                      ),
                    ),
                    const SizedBox(height: 8),
                    if ((post.createdAt ?? '').isNotEmpty)
                      Row(
                        children: [
                          const Icon(Icons.calendar_today_outlined, size: 16),
                          const SizedBox(width: 6),
                          Text(
                            post.createdAt!,
                            style: const TextStyle(color: Colors.black54),
                          ),
                        ],
                      ),
                    const SizedBox(height: 14),
                    Text(
                      post.contenidoPlano,
                      style: const TextStyle(fontSize: 15, height: 1.35),
                    ),
                  ],
                ),
              ),
            ),
          );
        },
      ),
    );
  }
}
