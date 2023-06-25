package hr.algebra.java.web.lego.legostorewithdata.repository;

import hr.algebra.java.web.lego.legostorewithdata.domain.CartItem;

import java.util.List;

public interface CartItemRepository {

    List<CartItem> getCartItems(String username);
    CartItem getCartItem(Long id);
    void updateCartItem(CartItem cartItem);

    void deleteCartItem(CartItem cartItem);

    void saveCartItem(CartItem cartItem);

    List<CartItem> getAllCartItems();

}
