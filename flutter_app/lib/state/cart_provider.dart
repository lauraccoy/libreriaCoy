import 'package:flutter/material.dart';

import 'cart_model.dart';

/// InheritedNotifier ligero para exponer el carrito sin dependencias externas.
class CartProvider extends InheritedNotifier<CartModel> {
  const CartProvider({
    super.key,
    required CartModel cart,
    required Widget child,
  }) : super(notifier: cart, child: child);

  static CartModel of(BuildContext context) {
    final provider =
        context.dependOnInheritedWidgetOfExactType<CartProvider>();
    assert(provider != null, 'No CartProvider found in context');
    return provider!.notifier!;
  }
}
