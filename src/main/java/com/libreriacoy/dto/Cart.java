package com.libreriacoy.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cart implements Serializable {
    private List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }

    public void addItem(ItemCatalogo item, int qty) {
        if (item == null || qty <= 0) return;
        for (CartItem ci : items) {
            if (ci.getItem().getId().equals(item.getId()) && ci.getItem().getTipoProducto().equals(item.getTipoProducto())) {
                ci.setQuantity(ci.getQuantity() + qty);
                return;
            }
        }
        items.add(new CartItem(item, qty));
    }

    public void removeItem(String tipoProducto, Long id) {
        Iterator<CartItem> it = items.iterator();
        while (it.hasNext()) {
            CartItem ci = it.next();
            if (ci.getItem().getId().equals(id) && ci.getItem().getTipoProducto().equals(tipoProducto)) {
                it.remove();
                return;
            }
        }
    }

    /**
     * Establece la cantidad exacta de un producto en el carrito.
     * Si qty <= 0, se elimina.
     */
    public void updateQuantity(String tipoProducto, Long id, int qty) {
        if (qty <= 0) {
            removeItem(tipoProducto, id);
            return;
        }
        for (CartItem ci : items) {
            if (ci.getItem().getId().equals(id) && ci.getItem().getTipoProducto().equals(tipoProducto)) {
                ci.setQuantity(qty);
                return;
            }
        }
    }

    public double getTotal() {
        return items.stream().mapToDouble(CartItem::getSubtotal).sum();
    }

    public int getTotalQuantity() {
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }
}
