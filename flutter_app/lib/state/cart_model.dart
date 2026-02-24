import 'package:flutter/foundation.dart';

import '../models/item_catalogo.dart';

class CartLine {
  final ItemCatalogo item;
  int quantity;

  CartLine({required this.item, this.quantity = 1});

  double get lineTotal => item.precio * quantity;
}

class CartModel extends ChangeNotifier {
  final List<CartLine> _lines = <CartLine>[];

  List<CartLine> get lines => List.unmodifiable(_lines);

  int get totalItems => _lines.fold<int>(0, (sum, l) => sum + l.quantity);

  double get totalPrice => _lines.fold<double>(0.0, (sum, l) => sum + l.lineTotal);

  bool get isEmpty => _lines.isEmpty;

  void add(ItemCatalogo item, {int qty = 1}) {
    final idx = _lines.indexWhere((l) => l.item.id == item.id);
    if (idx >= 0) {
      _lines[idx].quantity += qty;
    } else {
      _lines.add(CartLine(item: item, quantity: qty));
    }
    notifyListeners();
  }

  void remove(int itemId) {
    _lines.removeWhere((l) => l.item.id == itemId);
    notifyListeners();
  }

  void increment(int itemId) {
    final idx = _lines.indexWhere((l) => l.item.id == itemId);
    if (idx < 0) return;
    _lines[idx].quantity += 1;
    notifyListeners();
  }

  void decrement(int itemId) {
    final idx = _lines.indexWhere((l) => l.item.id == itemId);
    if (idx < 0) return;
    final q = _lines[idx].quantity;
    if (q <= 1) {
      _lines.removeAt(idx);
    } else {
      _lines[idx].quantity = q - 1;
    }
    notifyListeners();
  }

  void setQuantity(int itemId, int qty) {
    if (qty <= 0) {
      remove(itemId);
      return;
    }
    final idx = _lines.indexWhere((l) => l.item.id == itemId);
    if (idx < 0) return;
    _lines[idx].quantity = qty;
    notifyListeners();
  }

  void clear() {
    _lines.clear();
    notifyListeners();
  }
}
