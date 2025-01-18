package com.huseynov.restaurant.cart;

public interface CartService {

    Cart initializeNewCart();

    Cart getCart();

    Cart saveCart(Cart cart);

    void deleteCart();

    void clearCart();

    boolean isCustomerRequestValidForCart();

    void checkCustomerForCart();

    Long getCartId();

}































