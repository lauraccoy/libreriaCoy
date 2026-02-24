package com.libreriacoy.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.libreriacoy.dto.Cart;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("cart")
    public Cart cart(HttpSession session) {
        // Compatibilidad: en algunos controladores el carrito se guardó como "CART"
        // y en otros como "cart". Leemos ambos para que el contador y la vista siempre funcionen.
        Cart cart = (Cart) session.getAttribute("CART");
        if (cart == null) {
            cart = (Cart) session.getAttribute("cart");
        }
        if (cart == null) {
            cart = new Cart();
        }
        // Guardamos en ambos nombres para evitar desincronizaciones.
        session.setAttribute("CART", cart);
        session.setAttribute("cart", cart);
        return cart;
    }

    // Para la navbar (badge del carrito)
    @ModelAttribute("cartCount")
    public int cartCount(@ModelAttribute("cart") Cart cart) {
        return cart != null ? cart.getTotalQuantity() : 0;
    }
}
