package hr.algebra.java.web.lego.legostorewithdata.controller;


import hr.algebra.java.web.lego.legostorewithdata.domain.CartItem;
import hr.algebra.java.web.lego.legostorewithdata.domain.User;
import hr.algebra.java.web.lego.legostorewithdata.repository.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
@SessionAttributes("cartItems")
public class CartItemController {
    private final CartItemRepository cartItemRepository;

    @GetMapping("/user/{username}")
    public String viewCart(Model model,@ModelAttribute User user) {
        // Retrieve cart items from the repository

        List<CartItem> cartItems = cartItemRepository.getCartItems(user.getUsername());

        model.addAttribute("cartItems", cartItems);
        return "cartItemList.html";
    }

    @PostMapping("/cart-items/{id}/update")
    public String updateCartItemQuantity(@PathVariable("id") Long id, @RequestParam("quantity") int quantity) {
        CartItem cartItem = cartItemRepository.getCartItem(id);
        cartItem.setQuantity(quantity);
        cartItemRepository.updateCartItem(cartItem);
        return "redirect:/cart-items";
    }

    @PostMapping("/cart-items/{id}/delete")
    public String deleteCartItem(@PathVariable("id") Long id) {
        CartItem cartItem = cartItemRepository.getCartItem(id);
        cartItemRepository.deleteCartItem(cartItem);
        return "redirect:/cart-items";
    }
}



