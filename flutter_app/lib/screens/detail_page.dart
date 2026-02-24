import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart'; // kIsWeb
import 'package:cached_network_image/cached_network_image.dart';

import '../models/item_catalogo.dart';
import '../models/detalle_view.dart';
import '../services/api_service.dart';
import '../state/cart_provider.dart';
import '../widgets/cart_button.dart';

class DetailPage extends StatefulWidget {
  final ItemCatalogo item;

  const DetailPage({super.key, required this.item});

  @override
  State<DetailPage> createState() => _DetailPageState();
}

class _DetailPageState extends State<DetailPage> {
  bool _loading = true;
  String? _error;
  DetalleView? _detalle;

  @override
  void initState() {
    super.initState();
    _fetchDetalle();
  }

  Future<void> _fetchDetalle() async {
    setState(() {
      _loading = true;
      _error = null;
    });

    try {
      final tipo = _normalizeTipo(widget.item.tipoProducto);
      final detalle = await ApiService.fetchDetalle(tipo, widget.item.id);

      if (!mounted) return;
      setState(() {
        _detalle = detalle;
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

  // Enviamos tipos "limpios" a la API
  String _normalizeTipo(String tipo) {
    final lower = tipo.toLowerCase();
    // Ojo: la API puede distinguir tildes (Cómic / Papelería)
    if (lower.contains('comic') || lower.contains('cómic')) return 'Cómic';
    if (lower.contains('libro')) return 'Libro';
    if (lower.contains('papel')) return 'Papelería';
    if (lower.contains('geek')) return 'Geek';
    return tipo;
  }

  Widget _staticPlaceholder({double size = 28}) {
    return Container(
      color: Colors.black12,
      alignment: Alignment.center,
      child: Icon(Icons.image, size: size),
    );
  }

  Widget _productImage(String url) {
    final img = kIsWeb
        ? Image.network(
      url,
      fit: BoxFit.cover,
      loadingBuilder: (context, child, progress) {
        if (progress == null) return child;
        return _staticPlaceholder(size: 48);
      },
      errorBuilder: (context, error, stackTrace) {
        return _staticPlaceholder(size: 64);
      },
    )
        : CachedNetworkImage(
      imageUrl: url,
      placeholder: (context, _) => _staticPlaceholder(size: 48),
      errorWidget: (context, _, __) => _staticPlaceholder(size: 64),
      fit: BoxFit.cover,
      fadeInDuration: const Duration(milliseconds: 120),
    );

    return ClipRRect(
      borderRadius: BorderRadius.circular(8),
      child: img,
    );
  }

  @override
  Widget build(BuildContext context) {
    final cart = CartProvider.of(context);

    return Scaffold(
      appBar: AppBar(
        title: Text(widget.item.nombre),
        actions: const [CartButton()],
      ),
      body: _loading
      // ❌ Sin CircularProgressIndicator para evitar crashes en web
          ? const Center(child: Text('Cargando detalle...'))
          : (_error != null
          ? Center(child: Text('Error: $_error'))
          : (_detalle == null
          ? const Center(child: Text('No se encontró el detalle.'))
          : SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Center(
              child: SizedBox(
                width: 200,
                height: 280,
                child: _productImage(_detalle!.imagenOrPlaceholder()),
              ),
            ),
            const SizedBox(height: 16),

            Text(
              _detalle!.titulo ?? widget.item.nombre,
              style: const TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 6),
            Text('${_detalle!.tipo} • ${_detalle!.categoria ?? ''}'),
            const SizedBox(height: 6),

            if (_detalle!.precio != null)
              Text(
                '€ ${_detalle!.precio!.toStringAsFixed(2)}',
                style: const TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.w600,
                ),
              ),

            const Divider(height: 24),

            if (_detalle!.autor != null) _buildInfoRow('Autor', _detalle!.autor!),
            if (_detalle!.editorial != null) _buildInfoRow('Editorial', _detalle!.editorial!),
            if (_detalle!.isbn != null) _buildInfoRow('ISBN', _detalle!.isbn!),

            if (_detalle!.marca != null) _buildInfoRow('Marca', _detalle!.marca!),
            if (_detalle!.franquicia != null) _buildInfoRow('Franquicia', _detalle!.franquicia!),
            if (_detalle!.stock != null) _buildInfoRow('Stock', _detalle!.stock.toString()),

            const SizedBox(height: 24),

            ElevatedButton(
              onPressed: () {
                cart.add(widget.item);
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(
                    content: Text('Añadido al carrito: ${widget.item.nombre}'),
                    action: SnackBarAction(
                      label: 'Ver carrito',
                      onPressed: () => Navigator.pushNamed(context, '/cart'),
                    ),
                  ),
                );
              },
              child: const Text('Añadir al carrito'),
            ),
          ],
        ),
      ))),
    );
  }

  Widget _buildInfoRow(String label, String value) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 8.0),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          SizedBox(
            width: 110,
            child: Text('$label:', style: const TextStyle(fontWeight: FontWeight.w600)),
          ),
          Expanded(child: Text(value)),
        ],
      ),
    );
  }
}
