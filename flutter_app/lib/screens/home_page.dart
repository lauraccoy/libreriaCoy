import 'package:flutter/material.dart';
import 'catalog_page.dart';
import '../widgets/cart_button.dart';
import '../widgets/app_drawer.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  Widget _sectionCard({
    required BuildContext context,
    required String title,
    required String subtitle,
    required List<Color> colors,
    required String tipo,
  }) {
    return InkWell(
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (_) => CatalogPage(tipoInicial: tipo),
          ),
        );
      },
      borderRadius: BorderRadius.circular(22),
      child: Ink(
        height: 170,
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(22),
          gradient: LinearGradient(
            colors: colors,
            begin: Alignment.topLeft,
            end: Alignment.bottomRight,
          ),
          boxShadow: [
            BoxShadow(
              color: Colors.black.withOpacity(0.10),
              blurRadius: 14,
              offset: const Offset(0, 6),
            )
          ],
        ),
        child: Padding(
          padding: const EdgeInsets.all(20),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisAlignment: MainAxisAlignment.end,
            children: [
              Text(
                title,
                style: const TextStyle(
                  color: Colors.white,
                  fontSize: 23,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 4),
              Text(
                subtitle,
                style: const TextStyle(
                  color: Colors.white70,
                  fontSize: 13,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _hero() {
    return Container(
      width: double.infinity,
      padding: const EdgeInsets.all(26),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(24),
        gradient: const LinearGradient(
          colors: [
            Color(0xFF2E7D32),
            Color(0xFF145A32),
          ],
        ),
        boxShadow: [
          BoxShadow(
            color: Colors.green.withOpacity(0.25),
            blurRadius: 18,
            offset: const Offset(0, 8),
          )
        ],
      ),
      child: const Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            "Librería Coy",
            style: TextStyle(
              color: Colors.white,
              fontSize: 34,
              fontWeight: FontWeight.bold,
            ),
          ),
          SizedBox(height: 8),
          Text(
            "Libros, cómics, papelería y cultura geek en un solo lugar.",
            style: TextStyle(
              color: Colors.white70,
              fontSize: 16,
            ),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Librería Coy"),
        actions: const [
          CartButton(),
        ],
      ),

      // ✅ AQUÍ ESTÁ LO QUE TE FALTABA
      drawer: const AppDrawer(),

      body: SingleChildScrollView(
        padding: const EdgeInsets.all(18),
        child: Column(
          children: [
            _hero(),
            const SizedBox(height: 28),
            const Align(
              alignment: Alignment.centerLeft,
              child: Text(
                "Nuestras categorías",
                style: TextStyle(
                  fontSize: 26,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
            const SizedBox(height: 18),
            GridView.count(
              crossAxisCount: 2,
              crossAxisSpacing: 16,
              mainAxisSpacing: 16,
              shrinkWrap: true,
              physics: const NeverScrollableScrollPhysics(),
              childAspectRatio: 1.35,
              children: [
                _sectionCard(
                  context: context,
                  title: "Libros",
                  subtitle: "Narrativa, ensayo, infantil...",
                  colors: const [
                    Color(0xFF5B6EFF),
                    Color(0xFF2A2E83),
                  ],
                  tipo: "Libro",
                ),
                _sectionCard(
                  context: context,
                  title: "Cómics",
                  subtitle: "Manga, superhéroes y más",
                  colors: const [
                    Color(0xFFFF6CAB),
                    Color(0xFF8A2D3B),
                  ],
                  tipo: "Comic",
                ),
                _sectionCard(
                  context: context,
                  title: "Papelería",
                  subtitle: "Material escolar y oficina",
                  colors: const [
                    Color(0xFF1CB5E0),
                    Color(0xFF003C73),
                  ],
                  tipo: "Papeleria",
                ),
                _sectionCard(
                  context: context,
                  title: "Geek",
                  subtitle: "Figuras, juegos y merch",
                  colors: const [
                    Color(0xFFFFB347),
                    Color(0xFFB35600),
                  ],
                  tipo: "Geek",
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
