package com.libreriacoy.controller;

import com.libreriacoy.dto.Cart;
import com.libreriacoy.dto.ItemCatalogo;
import com.libreriacoy.service.CatalogoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CartController {

    private final CatalogoService catalogoService;

    public CartController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    private Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("CART");
        if (cart == null) cart = (Cart) session.getAttribute("cart");
        if (cart == null) cart = new Cart();

        session.setAttribute("CART", cart);
        session.setAttribute("cart", cart);
        return cart;
    }

    @PostMapping("/carrito/add")
    public String addToCart(@RequestParam String tipo,
                            @RequestParam Long id,
                            @RequestParam(defaultValue = "1") Integer cantidad,
                            HttpSession session,
                            RedirectAttributes ra) {

        ItemCatalogo item = catalogoService.obtenerItem(tipo, id);
        if (item == null) {
            ra.addFlashAttribute("error", "Producto no encontrado");
            return "redirect:/catalogo";
        }

        Cart cart = getCart(session);
        cart.addItem(item, cantidad);
        ra.addFlashAttribute("success", "Producto añadido al carrito");
        return "redirect:/carrito";
    }

    @GetMapping("/carrito")
    public String viewCart(HttpSession session, Model model) {
        Cart cart = getCart(session);
        model.addAttribute("cart", cart);
        model.addAttribute("contenido", "carrito");
        model.addAttribute("titulo", "Carrito de Compra");
        return "layout";
    }

    @PostMapping("/carrito/remove")
    public String removeFromCart(@RequestParam String tipo,
                                 @RequestParam Long id,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        Cart cart = getCart(session);
        cart.removeItem(tipo, id);
        ra.addFlashAttribute("success", "Producto eliminado del carrito");
        return "redirect:/carrito";
    }

    @PostMapping("/carrito/update")
    public String updateQuantity(@RequestParam String tipo,
                                 @RequestParam Long id,
                                 @RequestParam Integer cantidad,
                                 HttpSession session) {
        Cart cart = getCart(session);
        cart.updateQuantity(tipo, id, cantidad);
        return "redirect:/carrito";
    }
}
