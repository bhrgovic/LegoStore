package hr.algebra.java.web.lego.legostorewithdata.controller;


import hr.algebra.java.web.lego.legostorewithdata.domain.CartItem;
import hr.algebra.java.web.lego.legostorewithdata.domain.User;
import hr.algebra.java.web.lego.legostorewithdata.repository.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
@SessionAttributes("cartItems")
public class CartItemController {
    private final CartItemRepository cartItemRepository;

    @GetMapping("/user/{username}")
    public String viewCart(Model model, @PathVariable String username) {
        // Retrieve cart items from the repository
        List<CartItem> cartItems = cartItemRepository.getCartItems(username);
        BigDecimal totalPrice = cartItems.stream()
                .map(item -> item.getLego().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("cartItems", cartItems);
        return "cartItemList.html";
    }

    @PostMapping("/updateItem/{id}")
    public String updateCartItemQuantity(@ModelAttribute CartItem cartItem) {
        cartItemRepository.updateCartItem(cartItem);
        return "redirect:/cart/user/" + cartItem.getUsernamefk();
    }

    @PostMapping("/deleteItem/{id}")
    public String deleteCartItem(@ModelAttribute CartItem cartItem) {

        cartItemRepository.deleteCartItem(cartItem);
        return "redirect:/cartItemList.html";
    }

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model) {
        BigDecimal totalPrice = (BigDecimal) model.getAttribute("totalPrice");
        model.addAttribute("totalPrice", totalPrice);
        return "checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam("paymentMethod") String paymentMethod) {
        // Process the selected payment method
        if (paymentMethod.equals("cash")) {
            // Perform cash payment processing
            return "redirect:/purchase-successful";
        } else if (paymentMethod.equals("paypal")) {
            // Perform PayPal payment processing
            return "redirect:/paypal-redirect"; // Replace with the actual PayPal redirect URL
        } else {
            // Handle invalid payment method
            return "redirect:/cart/user/{username}"; // Redirect back to the cart with an error message, if needed
        }
    }

}



