package com.libreriacoy.dto;

import java.io.Serializable;

public class CartItem implements Serializable {

    private ItemCatalogo item;
    private int quantity;

    public CartItem() {}

    public CartItem(ItemCatalogo item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public ItemCatalogo getItem() {
        return item;
    }

    public void setItem(ItemCatalogo item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        if (item == null || item.getPrecio() == null) return 0.0;
        return item.getPrecio() * quantity;
    }
}
