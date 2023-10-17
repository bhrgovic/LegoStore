package hr.algebra.java.web.lego.legostorewithdata.controller;


import hr.algebra.java.web.lego.legostorewithdata.domain.CartItem;
import hr.algebra.java.web.lego.legostorewithdata.domain.OrderHistory;
import hr.algebra.java.web.lego.legostorewithdata.domain.User;
import hr.algebra.java.web.lego.legostorewithdata.repository.CartItemRepository;
import hr.algebra.java.web.lego.legostorewithdata.repository.OrderHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
@SessionAttributes("cartItems")
public class CartItemController {
    private final CartItemRepository cartItemRepository;
    private final OrderHistoryRepository orderHistoryRepository;


    @GetMapping("/getUser")
    public String getCart(Principal principal,Model model){
        String username = principal.getName();
        return "redirect:/cart/user/" + username;
    }

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

    @GetMapping("/updateItem/{id}")
    public String showUpdateForm(@PathVariable("id") int cartItemId, Model model){
        CartItem cartItem = cartItemRepository.getCartItem(cartItemId);
        model.addAttribute("cartItem", cartItem);
        return "updateCartForm";
    }

    @PostMapping("/updateItem/{id}")
    public String updateCartItemQuantity(@PathVariable("id") int cartItemId, @ModelAttribute CartItem updatedCartItem) {
        CartItem existingCartItem = cartItemRepository.getCartItem(cartItemId);
        existingCartItem.setQuantity(updatedCartItem.getQuantity());
        // Update other fields as needed

        cartItemRepository.updateCartItem(existingCartItem);
        return "redirect:/cart/user/" + existingCartItem.getUsernamefk();
    }



    @GetMapping("/deleteItem/{id}")
    public String deleteCartItem(@ModelAttribute CartItem cartItem) {
        CartItem existingCartItem = cartItemRepository.getCartItem(cartItem.getId());
        cartItemRepository.deleteCartItem(cartItem);
        return "redirect:/cart/user/" + existingCartItem.getUsernamefk();
    }

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model) {
        BigDecimal totalPrice = (BigDecimal) model.getAttribute("totalPrice");
        model.addAttribute("totalPrice", totalPrice);
        return "checkout";
    }

    @PostMapping("/checkout1")
    public String processCheckout(@RequestParam("paymentMethod") String paymentMethod, Principal principal,Model model) {
        // Process the selected payment method
        String username = principal.getName(); // Get the username of the current user
        List<CartItem> cartItems = cartItemRepository.getCartItems(username); // Get all cart items for the user
        BigDecimal totalPrice = calculateTotalPrice(cartItems); // Calculate the total price
        OrderHistory oh=new OrderHistory(username,totalPrice);
        if (paymentMethod.equals("cash")) {
            // Perform cash payment processing
            orderHistoryRepository.saveOrderHistory(oh);
            for(CartItem cartItem :cartItems){
                cartItemRepository.deleteCartItem(cartItem);
            }
            return "redirect:/lego/homePageUser.html";
        } else if (paymentMethod.equals("paypal")) {
            //to do

            return "redirect:/paypal/checkout-pay.html"; // Replace with the actual PayPal redirect URL
        } else {
            // to do
            return "redirect:/cart/user/{username}"; // Redirect back to the cart with an error message, if needed
        }

    }

    private BigDecimal calculateTotalPrice(List<CartItem> cartItems) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            BigDecimal itemPrice = cartItem.getLego().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalPrice = totalPrice.add(itemPrice);
        }
        return totalPrice;
    }
}



