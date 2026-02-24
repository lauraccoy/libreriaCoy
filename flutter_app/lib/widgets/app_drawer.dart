import 'package:flutter/material.dart';
import '../screens/home_page.dart';
import '../screens/catalog_page.dart';
import '../screens/login_page.dart';
import '../screens/register_page.dart';
import '../screens/blog_list_page.dart';
import '../services/session_manager.dart';

class AppDrawer extends StatefulWidget {
  const AppDrawer({super.key});

  @override
  State<AppDrawer> createState() => _AppDrawerState();
}

class _AppDrawerState extends State<AppDrawer> {
  String? _email;
  String? _token;
  bool _loading = true;

  @override
  void initState() {
    super.initState();
    _loadSession();
  }

  Future<void> _loadSession() async {
    final token = await SessionManager.loadToken();
    final email = await SessionManager.loadEmail();

    if (!mounted) return;
    setState(() {
      _token = token;
      _email = email;
      _loading = false;
    });
  }

  bool get _isLoggedIn => _token != null && _token!.isNotEmpty;

  Future<void> _logout() async {
    await SessionManager.clear();
    if (!mounted) return;

    // Cierra drawer
    Navigator.pop(context);

    // Vuelve a home
    Navigator.pushAndRemoveUntil(
      context,
      MaterialPageRoute(builder: (_) => const HomePage()),
          (route) => false,
    );
  }

  // ✅ CAMBIO CLAVE: NO usar pushReplacement
  void _go(Widget page) {
    Navigator.pop(context);
    Navigator.push(
      context,
      MaterialPageRoute(builder: (_) => page),
    );
  }

  void _goNamed(String route) {
    Navigator.pop(context);
    Navigator.pushNamed(context, route);
  }

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: ListView(
        padding: EdgeInsets.zero,
        children: [
          DrawerHeader(
            decoration: const BoxDecoration(color: Color(0xFF1F6B4E)),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                const Icon(Icons.menu_book_rounded, color: Colors.white, size: 34),
                const SizedBox(height: 10),
                const Text(
                  'Librería Coy',
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                const SizedBox(height: 4),
                Text(
                  _loading
                      ? 'Cargando...'
                      : (_isLoggedIn ? (_email ?? 'Sesión iniciada') : 'Catálogo'),
                  style: const TextStyle(color: Colors.white70),
                ),
              ],
            ),
          ),

          // ✅ Menú principal (siempre visible)
          ListTile(
            leading: const Icon(Icons.home),
            title: const Text('Portada'),
            onTap: () => _go(const HomePage()),
          ),
          ListTile(
            leading: const Icon(Icons.grid_on),
            title: const Text('Catálogo'),
            onTap: () => _go(const CatalogPage()),
          ),
          ListTile(
            leading: const Icon(Icons.article_outlined),
            title: const Text('Blog'),
            onTap: () => _go(const BlogListPage()),
          ),
          ListTile(
            leading: const Icon(Icons.shopping_cart),
            title: const Text('Carrito'),
            onTap: () => _goNamed('/cart'),
          ),

          const Divider(),

          // ✅ Auth
          if (!_loading && !_isLoggedIn) ...[
            ListTile(
              leading: const Icon(Icons.login),
              title: const Text('Iniciar sesión'),
              onTap: () async {
                Navigator.pop(context);
                await Navigator.push(
                  context,
                  MaterialPageRoute(builder: (_) => const LoginPage()),
                );
                await _loadSession();
              },
            ),
            ListTile(
              leading: const Icon(Icons.person_add_alt_1),
              title: const Text('Crear cuenta'),
              onTap: () async {
                Navigator.pop(context);
                await Navigator.push(
                  context,
                  MaterialPageRoute(builder: (_) => const RegisterPage()),
                );
                await _loadSession();
              },
            ),
          ] else if (!_loading && _isLoggedIn) ...[
            ListTile(
              leading: const Icon(Icons.logout),
              title: const Text('Cerrar sesión'),
              onTap: _logout,
            ),
          ],

          const Divider(),

          const Padding(
            padding: EdgeInsets.symmetric(horizontal: 16, vertical: 8),
            child: Text('Secciones', style: TextStyle(fontWeight: FontWeight.bold)),
          ),

          ListTile(
            title: const Text('Libros'),
            onTap: () => _go(const CatalogPage(categoriaFiltro: 'Libro')),
          ),
          ListTile(
            title: const Text('Cómics'),
            onTap: () => _go(const CatalogPage(categoriaFiltro: 'Cómic')),
          ),
          ListTile(
            title: const Text('Papelería'),
            onTap: () => _go(const CatalogPage(categoriaFiltro: 'Papelería')),
          ),
          ListTile(
            title: const Text('Geek'),
            onTap: () => _go(const CatalogPage(categoriaFiltro: 'Geek')),
          ),
        ],
      ),
    );
  }
}
