import 'package:flutter/material.dart';
import 'screens/home_page.dart';
import 'screens/cart_page.dart';
import 'screens/blog_list_page.dart';

import 'state/cart_model.dart';
import 'state/cart_provider.dart';

void main() {
  runApp(const LibreriaCoyApp());
}

class LibreriaCoyApp extends StatelessWidget {
  const LibreriaCoyApp({super.key});

  // Carrito global (para una demo simple sin dependencias externas).
  static final CartModel _cart = CartModel();

  @override
  Widget build(BuildContext context) {
    return CartProvider(
      cart: _cart,
      child: MaterialApp(
        title: 'LibreriaCoy',
        theme: ThemeData(
          colorScheme: ColorScheme.fromSeed(
            seedColor: const Color(0xFF1F6B4E),
            primary: const Color(0xFF1F6B4E),
            secondary: const Color(0xFFFFC107),
          ),
          useMaterial3: true,
          scaffoldBackgroundColor: const Color(0xFFF6F7F9),
          appBarTheme: const AppBarTheme(
            backgroundColor: Color(0xFF1F6B4E),
            foregroundColor: Colors.white,
            centerTitle: false,
          ),
          drawerTheme: const DrawerThemeData(
            backgroundColor: Colors.white,
          ),
          cardTheme: CardTheme(
            elevation: 2,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(16),
            ),
          ),
          elevatedButtonTheme: ElevatedButtonThemeData(
            style: ElevatedButton.styleFrom(
              backgroundColor: const Color(0xFFFFC107),
              foregroundColor: Colors.black,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(12),
              ),
              textStyle: const TextStyle(fontWeight: FontWeight.w600),
            ),
          ),
          filledButtonTheme: FilledButtonThemeData(
            style: FilledButton.styleFrom(
              backgroundColor: const Color(0xFFFFC107),
              foregroundColor: Colors.black,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(12),
              ),
              textStyle: const TextStyle(fontWeight: FontWeight.w600),
            ),
          ),
        ),
        home: const HomePage(),
        routes: {
          '/cart': (context) => const CartPage(),
          '/blog': (context) => const BlogListPage(),
        },
      ),
    );
  }
}
