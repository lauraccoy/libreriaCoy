import 'dart:convert';
import 'package:http/http.dart' as http;

import 'api_service.dart';
import 'session_manager.dart';

class AuthService {
  static Uri _u(String path) => Uri.parse('${ApiService.baseUrl}$path');

  /// Login: guarda token + email en SharedPreferences
  static Future<void> login({
    required String email,
    required String password,
  }) async {
    final res = await http.post(
      _u('/api/auth/login'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'email': email.trim().toLowerCase(),
        'password': password,
      }),
    );

    if (res.statusCode != 200) {
      throw Exception('Login inválido (${res.statusCode})');
    }

    final data = jsonDecode(res.body);

    // Admitimos varios formatos comunes:
    final token = (data['token'] ?? data['accessToken'] ?? data['jwt'])?.toString();
    if (token == null || token.isEmpty) {
      throw Exception('El backend no devolvió token');
    }

    await SessionManager.saveSession(token: token, email: email.trim().toLowerCase());
  }

  /// Register: crea usuario y (opcional) loguea
  static Future<void> register({
    required String nombre,
    required String email,
    required String password,
  }) async {
    final res = await http.post(
      _u('/api/auth/register'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'nombre': nombre.trim(),
        'email': email.trim().toLowerCase(),
        'password': password,
      }),
    );

    if (res.statusCode != 200 && res.statusCode != 201) {
      throw Exception('No se pudo registrar (${res.statusCode})');
    }

    // Si tu backend ya devuelve token en register, lo guardamos.
    try {
      final data = jsonDecode(res.body);
      final token = (data['token'] ?? data['accessToken'] ?? data['jwt'])?.toString();
      if (token != null && token.isNotEmpty) {
        await SessionManager.saveSession(token: token, email: email.trim().toLowerCase());
      }
    } catch (_) {
      // si no devuelve JSON, no pasa nada
    }
  }

  static Future<void> logout() => SessionManager.clear();
}
