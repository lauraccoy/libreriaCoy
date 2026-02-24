import 'package:flutter/material.dart';

import '../state/cart_provider.dart';

/// Botón de carrito con badge de cantidad.
class CartButton extends StatelessWidget {
  final VoidCallback? onPressed;

  const CartButton({super.key, this.onPressed});

  @override
  Widget build(BuildContext context) {
    final cart = CartProvider.of(context);

    return AnimatedBuilder(
      animation: cart,
      builder: (context, _) {
        final count = cart.totalItems;
        return Stack(
          alignment: Alignment.center,
          children: [
            IconButton(
              tooltip: 'Carrito',
              icon: const Icon(Icons.shopping_cart),
              onPressed: onPressed ?? () => Navigator.pushNamed(context, '/cart'),
            ),
            if (count > 0)
              Positioned(
                right: 8,
                top: 8,
                child: Container(
                  padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 2),
                  decoration: BoxDecoration(
                    color: Theme.of(context).colorScheme.secondary,
                    borderRadius: BorderRadius.circular(10),
                  ),
                  child: Text(
                    '$count',
                    style: const TextStyle(
                      color: Colors.black,
                      fontSize: 11,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
              ),
          ],
        );
      },
    );
  }
}
