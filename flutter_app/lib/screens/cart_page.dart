import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';
import 'package:cached_network_image/cached_network_image.dart';

import '../state/cart_provider.dart';
import '../widgets/app_drawer.dart';

class CartPage extends StatelessWidget {
  const CartPage({super.key});

  Widget _staticPlaceholder() {
    return Container(
      color: Colors.black12,
      alignment: Alignment.center,
      child: const Icon(Icons.image, size: 24),
    );
  }

  Widget _thumb(String url) {
    final w = kIsWeb
        ? Image.network(
      url,
      fit: BoxFit.cover,
      loadingBuilder: (context, child, progress) {
        if (progress == null) return child;
        return _staticPlaceholder();
      },
      errorBuilder: (context, error, stack) => _staticPlaceholder(),
    )
        : CachedNetworkImage(
      imageUrl: url,
      placeholder: (context, _) => _staticPlaceholder(),
      errorWidget: (context, _, __) => _staticPlaceholder(),
      fit: BoxFit.cover,
      fadeInDuration: const Duration(milliseconds: 120),
    );

    return ClipRRect(
      borderRadius: BorderRadius.circular(6),
      child: w,
    );
  }

  @override
  Widget build(BuildContext context) {
    final cart = CartProvider.of(context);

    return AnimatedBuilder(
      animation: cart,
      builder: (context, _) {
        return Scaffold(
          appBar: AppBar(
            title: Text('Carrito (${cart.totalItems})'),
            actions: [
              if (!cart.isEmpty)
                IconButton(
                  tooltip: 'Vaciar carrito',
                  icon: const Icon(Icons.delete_sweep),
                  onPressed: () {
                    showDialog<void>(
                      context: context,
                      builder: (ctx) => AlertDialog(
                        title: const Text('Vaciar carrito'),
                        content: const Text('¿Quieres eliminar todos los productos del carrito?'),
                        actions: [
                          TextButton(
                            onPressed: () => Navigator.pop(ctx),
                            child: const Text('Cancelar'),
                          ),
                          ElevatedButton(
                            onPressed: () {
                              cart.clear();
                              Navigator.pop(ctx);
                            },
                            child: const Text('Vaciar'),
                          ),
                        ],
                      ),
                    );
                  },
                ),
            ],
          ),

          // ✅ Drawer unificado
          drawer: const AppDrawer(),

          body: cart.isEmpty
              ? Center(
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                const Icon(Icons.shopping_cart_outlined, size: 48),
                const SizedBox(height: 12),
                Text(
                  'Tu carrito está vacío',
                  style: Theme.of(context).textTheme.titleMedium,
                ),
                const SizedBox(height: 12),
                ElevatedButton.icon(
                  onPressed: () => Navigator.pop(context),
                  icon: const Icon(Icons.storefront),
                  label: const Text('Volver al catálogo'),
                ),
              ],
            ),
          )
              : Column(
            children: [
              Expanded(
                child: ListView.separated(
                  padding: const EdgeInsets.all(12),
                  itemCount: cart.lines.length,
                  separatorBuilder: (_, __) => const SizedBox(height: 10),
                  itemBuilder: (context, i) {
                    final line = cart.lines[i];
                    final it = line.item;

                    return Dismissible(
                      key: ValueKey(it.id),
                      direction: DismissDirection.endToStart,
                      background: Container(
                        alignment: Alignment.centerRight,
                        padding: const EdgeInsets.only(right: 16),
                        color: Colors.red.shade600,
                        child: const Icon(Icons.delete, color: Colors.white),
                      ),
                      onDismissed: (_) {
                        cart.remove(it.id);
                        ScaffoldMessenger.of(context).showSnackBar(
                          SnackBar(content: Text('Eliminado: ${it.nombre}')),
                        );
                      },
                      child: Card(
                        child: Padding(
                          padding: const EdgeInsets.all(10),
                          child: Row(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              SizedBox(
                                width: 72,
                                height: 72,
                                child: _thumb(it.imagen),
                              ),
                              const SizedBox(width: 10),
                              Expanded(
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Text(
                                      it.nombre,
                                      maxLines: 2,
                                      overflow: TextOverflow.ellipsis,
                                      style: const TextStyle(fontWeight: FontWeight.w600),
                                    ),
                                    const SizedBox(height: 4),
                                    Text(
                                      '€ ${it.precio.toStringAsFixed(2)}',
                                      style: TextStyle(color: Colors.grey.shade800),
                                    ),
                                    const SizedBox(height: 8),
                                    Row(
                                      children: [
                                        _QtyButton(
                                          icon: Icons.remove,
                                          onTap: () => cart.decrement(it.id),
                                        ),
                                        Padding(
                                          padding: const EdgeInsets.symmetric(horizontal: 12),
                                          child: Text(
                                            '${line.quantity}',
                                            style: const TextStyle(fontSize: 16, fontWeight: FontWeight.w600),
                                          ),
                                        ),
                                        _QtyButton(
                                          icon: Icons.add,
                                          onTap: () => cart.increment(it.id),
                                        ),
                                        const Spacer(),
                                        Text(
                                          '€ ${line.lineTotal.toStringAsFixed(2)}',
                                          style: const TextStyle(fontWeight: FontWeight.bold),
                                        ),
                                      ],
                                    ),
                                  ],
                                ),
                              ),
                              IconButton(
                                tooltip: 'Eliminar',
                                icon: const Icon(Icons.close),
                                onPressed: () => cart.remove(it.id),
                              ),
                            ],
                          ),
                        ),
                      ),
                    );
                  },
                ),
              ),
              _CartSummary(
                total: cart.totalPrice,
                onCheckout: () {
                  showDialog<void>(
                    context: context,
                    builder: (ctx) => AlertDialog(
                      title: const Text('Pedido (simulado)'),
                      content: Text(
                        'Total: € ${cart.totalPrice.toStringAsFixed(2)}\n\n'
                            'Aquí iría el flujo de pago/confirmación.',
                      ),
                      actions: [
                        TextButton(
                          onPressed: () => Navigator.pop(ctx),
                          child: const Text('Cerrar'),
                        ),
                        ElevatedButton(
                          onPressed: () {
                            cart.clear();
                            Navigator.pop(ctx);
                          },
                          child: const Text('Finalizar y vaciar'),
                        )
                      ],
                    ),
                  );
                },
              ),
            ],
          ),
        );
      },
    );
  }
}

class _QtyButton extends StatelessWidget {
  final IconData icon;
  final VoidCallback onTap;

  const _QtyButton({required this.icon, required this.onTap});

  @override
  Widget build(BuildContext context) {
    return InkResponse(
      onTap: onTap,
      radius: 20,
      child: Container(
        width: 32,
        height: 32,
        decoration: BoxDecoration(
          border: Border.all(color: Colors.black12),
          borderRadius: BorderRadius.circular(8),
        ),
        alignment: Alignment.center,
        child: Icon(icon, size: 18),
      ),
    );
  }
}

class _CartSummary extends StatelessWidget {
  final double total;
  final VoidCallback onCheckout;

  const _CartSummary({required this.total, required this.onCheckout});

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      top: false,
      child: Container(
        padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
        decoration: BoxDecoration(
          border: Border(top: BorderSide(color: Colors.grey.shade300)),
        ),
        child: Row(
          children: [
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisSize: MainAxisSize.min,
                children: [
                  const Text('Total', style: TextStyle(fontWeight: FontWeight.w600)),
                  const SizedBox(height: 2),
                  Text(
                    '€ ${total.toStringAsFixed(2)}',
                    style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                  ),
                ],
              ),
            ),
            ElevatedButton.icon(
              onPressed: onCheckout,
              icon: const Icon(Icons.payment),
              label: const Text('Tramitar'),
            ),
          ],
        ),
      ),
    );
  }
}
