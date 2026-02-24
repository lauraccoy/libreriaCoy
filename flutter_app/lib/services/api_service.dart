import 'dart:convert';
import 'package:http/http.dart' as http;

import '../models/item_catalogo.dart';
import '../models/detalle_view.dart';
import '../models/blog_post.dart';
import 'session_manager.dart';

class ApiService {
  // ✅ IMPORTANTE:
  // - Android emulator: http://10.0.2.2:8080
  // - iOS simulator / desktop / web: http://localhost:8080
  static const String baseUrl = 'http://localhost:8080';

  static Future<Map<String, String>> _headers() async {
    final token = await SessionManager.loadToken();
    final headers = <String, String>{
      'Content-Type': 'application/json',
    };
    if (token != null && token.isNotEmpty) {
      headers['Authorization'] = 'Bearer $token';
    }
    return headers;
  }

  // ✅ CLAVE: fuerza UTF-8 siempre
  static String _utf8Body(http.Response res) => utf8.decode(res.bodyBytes);

  static Future<List<ItemCatalogo>> fetchCatalogo() async {
    final uri = Uri.parse('$baseUrl/api/catalogo');
    final res = await http.get(uri, headers: await _headers());

    if (res.statusCode != 200) {
      throw Exception('Error cargando catálogo (${res.statusCode})');
    }

    final List data = jsonDecode(_utf8Body(res));
    return data.map((e) => ItemCatalogo.fromJson(e)).toList();
  }

  static Future<List<ItemCatalogo>> fetchCatalogoPorTipo(String tipo) async {
    final uri = Uri.parse('$baseUrl/api/catalogo?tipo=$tipo');
    final res = await http.get(uri, headers: await _headers());

    if (res.statusCode != 200) {
      throw Exception('Error cargando catálogo por tipo (${res.statusCode})');
    }

    final List data = jsonDecode(_utf8Body(res));
    return data.map((e) => ItemCatalogo.fromJson(e)).toList();
  }

  static Future<DetalleView> fetchDetalle(String tipo, int id) async {
    final uri = Uri.parse('$baseUrl/api/detalle?tipo=$tipo&id=$id');
    final res = await http.get(uri, headers: await _headers());

    if (res.statusCode != 200) {
      throw Exception('Error cargando detalle (${res.statusCode})');
    }

    final data = jsonDecode(_utf8Body(res));
    return DetalleView.fromJson(data);
  }

  // =========================
  // ✅ BLOG
  // =========================

  static Future<List<BlogPost>> fetchBlogPosts() async {
    final uri = Uri.parse('$baseUrl/api/blog');
    final res = await http.get(uri, headers: await _headers());

    if (res.statusCode != 200) {
      throw Exception('Error cargando blog (${res.statusCode})');
    }

    final List data = jsonDecode(_utf8Body(res));
    return data.map((e) => BlogPost.fromJson(e)).toList();
  }

  static Future<BlogPost> fetchBlogPostBySlug(String slug) async {
    final uri = Uri.parse('$baseUrl/api/blog/$slug');
    final res = await http.get(uri, headers: await _headers());

    if (res.statusCode != 200) {
      throw Exception('Error cargando artículo (${res.statusCode})');
    }

    final data = jsonDecode(_utf8Body(res));
    return BlogPost.fromJson(data);
  }
}
