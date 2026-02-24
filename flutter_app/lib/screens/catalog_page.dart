import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart'; // kIsWeb
import 'package:cached_network_image/cached_network_image.dart';

import '../models/item_catalogo.dart';
import '../services/api_service.dart';
import 'detail_page.dart';
import '../widgets/cart_button.dart';
import '../widgets/app_drawer.dart';

class CatalogPage extends StatefulWidget {
  /// Filtro que usas desde el drawer o desde rutas existentes
  final String? categoriaFiltro;

  /// Alias para poder entrar a una categoría desde Home (si lo estás usando allí)
  final String? tipoInicial;

  const CatalogPage({
    super.key,
    this.categoriaFiltro,
    this.tipoInicial,
  });

  @override
  State<CatalogPage> createState() => _CatalogPageState();
}

class _CatalogPageState extends State<CatalogPage> {
  bool _loading = true;
  String? _error;
  List<ItemCatalogo> _items = [];

  @override
  void initState() {
    super.initState();
    _load();
  }

  String _normalize(String s) => s
      .toLowerCase()
      .trim()
      .replaceAll('á', 'a')
      .replaceAll('é', 'e')
      .replaceAll('í', 'i')
      .replaceAll('ó', 'o')
      .replaceAll('ú', 'u')
      .replaceAll('ü', 'u')
      .replaceAll('ñ', 'n');

  Future<void> _load() async {
    setState(() {
      _loading = true;
      _error = null;
    });

    try {
      final items = await ApiService.fetchCatalogo();

      // Acepta filtro por "categoriaFiltro" o por "tipoInicial"
      final filterRaw = widget.categoriaFiltro ?? widget.tipoInicial;

      List<ItemCatalogo> filtered;
      if (filterRaw == null || filterRaw.isEmpty) {
        filtered = items;
      } else {
        final f = _normalize(filterRaw);

        filtered = items.where((i) {
          final t = _normalize(i.tipoProducto);
          final c = _normalize(i.categoria);

          // Coincidencia flexible para que funcionen:
          // "Comic", "Cómic", "Comics", "Cómics", etc.
          bool match(String value) {
            if (value.contains(f)) return true;
            if (f.contains(value)) return true;

            final valueSing = value.endsWith('s') ? value.substring(0, value.length - 1) : value;
            final fSing = f.endsWith('s') ? f.substring(0, f.length - 1) : f;

            return valueSing.contains(fSing) || fSing.contains(valueSing);
          }

          return match(t) || match(c);
        }).toList();
      }

      if (!mounted) return;
      setState(() {
        _items = filtered;
        _loading = false;
      });
    } catch (e) {
      if (!mounted) return;
      setState(() {
        _error = e.toString();
        _loading = false;
      });
    }
  }

  Widget _staticPlaceholder() {
    return Container(
      color: Colors.black12,
      alignment: Alignment.center,
      child: const Icon(Icons.image, size: 28),
    );
  }

  /// Imagen del producto:
  /// - En WEB: Image.network (más estable)
  /// - En móvil/escritorio: CachedNetworkImage (con caché)
  Widget _productImage(String url) {
    final child = kIsWeb
        ? Image.network(
      url,
      fit: BoxFit.cover,
      loadingBuilder: (context, widget, loadingProgress) {
        if (loadingProgress == null) return widget;
        return _staticPlaceholder();
      },
      errorBuilder: (context, error, stackTrace) {
        return Container(
          color: Colors.black12,
          alignment: Alignment.center,
          child: const Icon(Icons.image_not_supported),
        );
      },
    )
        : CachedNetworkImage(
      imageUrl: url,
      placeholder: (context, _) => _staticPlaceholder(),
      errorWidget: (context, _, __) => Container(
        color: Colors.black12,
        alignment: Alignment.center,
        child: const Icon(Icons.image_not_supported),
      ),
      fit: BoxFit.cover,
      fadeInDuration: const Duration(milliseconds: 120),
    );

    return ClipRRect(
      borderRadius: BorderRadius.circular(6),
      child: child,
    );
  }

  @override
  Widget build(BuildContext context) {
    final filtro = widget.categoriaFiltro ?? widget.tipoInicial;
    final titulo = (filtro == null || filtro.isEmpty) ? 'Catálogo' : 'Catálogo - $filtro';

    return Scaffold(
      appBar: AppBar(
        title: Text(titulo),
        actions: const [CartButton()],
      ),

      // ✅ Drawer unificado (aquí estaba el problema)
      drawer: const AppDrawer(),

      body: _loading
          ? const Center(child: Text('Cargando catálogo...'))
          : (_error != null
          ? Center(child: Text('Error: $_error'))
          : RefreshIndicator(
        onRefresh: _load,
        child: _items.isEmpty
            ? ListView(
          physics: const AlwaysScrollableScrollPhysics(),
          children: [
            const SizedBox(height: 80),
            Center(
              child: Text(
                'No hay productos para esta sección.',
                style: Theme.of(context).textTheme.titleMedium,
              ),
            ),
          ],
        )
            : ListView.builder(
          physics: const AlwaysScrollableScrollPhysics(),
          itemCount: _items.length,
          itemBuilder: (context, index) {
            final it = _items[index];

            return ListTile(
              leading: SizedBox(
                width: 60,
                height: 60,
                child: _productImage(it.imagen),
              ),
              title: Text(it.nombre),
              subtitle: Text('${it.tipoProducto} • ${it.categoria}'),
              trailing: Text('€ ${it.precio.toStringAsFixed(2)}'),
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (_) => DetailPage(item: it)),
                );
              },
            );
          },
        ),
      )),
    );
  }
}
