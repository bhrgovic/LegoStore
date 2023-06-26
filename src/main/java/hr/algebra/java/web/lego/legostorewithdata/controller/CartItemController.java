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

    @PostMapping("/updateItem/{id}")
    public String updateCartItemQuantity(@ModelAttribute CartItem cartItem) {

        cartItemRepository.updateCartItem(cartItem);
        return "redirect:/cartItemList.html";
    }

    @PostMapping("/deleteItem/{id}")
    public String deleteCartItem(@ModelAttribute CartItem cartItem) {

        cartItemRepository.deleteCartItem(cartItem);
        return "redirect:/cartItemList.html";
    }
}



