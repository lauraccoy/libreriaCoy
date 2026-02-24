import 'package:flutter/material.dart';
import '../services/api_service.dart';
import '../models/blog_post.dart';
import 'blog_detail_page.dart';
import '../widgets/app_drawer.dart';

class BlogListPage extends StatefulWidget {
  const BlogListPage({super.key});

  @override
  State<BlogListPage> createState() => _BlogListPageState();
}

class _BlogListPageState extends State<BlogListPage> {
  late Future<List<BlogPost>> _future;

  @override
  void initState() {
    super.initState();
    _future = ApiService.fetchBlogPosts();
  }

  Future<void> _reload() async {
    setState(() {
      _future = ApiService.fetchBlogPosts();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      drawer: const AppDrawer(),
      appBar: AppBar(title: const Text('Blog')),
      body: RefreshIndicator(
        onRefresh: _reload,
        child: FutureBuilder<List<BlogPost>>(
          future: _future,
          builder: (context, snap) {
            if (snap.connectionState == ConnectionState.waiting) {
              return const Center(child: CircularProgressIndicator());
            }
            if (snap.hasError) {
              return ListView(
                children: [
                  const SizedBox(height: 24),
                  const Icon(Icons.error_outline, size: 48),
                  const SizedBox(height: 12),
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 16),
                    child: Text(
                      'No se pudo cargar el blog.\n\n${snap.error}',
                      textAlign: TextAlign.center,
                    ),
                  ),
                  const SizedBox(height: 12),
                  Center(
                    child: FilledButton(
                      onPressed: _reload,
                      child: const Text('Reintentar'),
                    ),
                  ),
                ],
              );
            }

            final posts = snap.data ?? [];
            if (posts.isEmpty) {
              return ListView(
                children: const [
                  SizedBox(height: 24),
                  Center(child: Text('Aún no hay artículos publicados.')),
                ],
              );
            }

            return ListView.separated(
              padding: const EdgeInsets.all(12),
              itemCount: posts.length,
              separatorBuilder: (_, __) => const SizedBox(height: 10),
              itemBuilder: (context, i) {
                final p = posts[i];
                final resumen = (p.resumen ?? p.contenidoPlano);
                final resumenCorto = resumen.length > 140
                    ? '${resumen.substring(0, 140)}…'
                    : resumen;

                return Card(
                  child: InkWell(
                    borderRadius: BorderRadius.circular(16),
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (_) => BlogDetailPage(slug: p.slug),
                        ),
                      );
                    },
                    child: Padding(
                      padding: const EdgeInsets.all(14),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            p.titulo,
                            style: const TextStyle(
                              fontSize: 16,
                              fontWeight: FontWeight.w700,
                            ),
                          ),
                          const SizedBox(height: 8),
                          Text(
                            resumenCorto,
                            style: const TextStyle(color: Colors.black87),
                          ),
                          const SizedBox(height: 10),
                          Row(
                            children: [
                              const Icon(Icons.calendar_today_outlined, size: 16),
                              const SizedBox(width: 6),
                              Text(
                                p.createdAt ?? '',
                                style: const TextStyle(color: Colors.black54),
                              ),
                              const Spacer(),
                              const Icon(Icons.chevron_right),
                            ],
                          ),
                        ],
                      ),
                    ),
                  ),
                );
              },
            );
          },
        ),
      ),
    );
  }
}
