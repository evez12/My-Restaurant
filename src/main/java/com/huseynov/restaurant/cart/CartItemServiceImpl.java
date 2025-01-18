package com.huseynov.restaurant.cart;

import com.huseynov.restaurant.product.Product;
import com.huseynov.restaurant.product.ProductService;
import com.huseynov.restaurant.shared.exception.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final ProductService productService;


    /**
     * 1.if cart is not exist, create a new cart
     * 2. get the product
     * 3. check if the product already in the cart
     * 4. if yes, then increase the quantity with the requested quantity
     * 5. if no, then initiate a new CartItem entry
     * 6. set the total price of the cartItem
     * 7. return the cart
     */

    @Override
    public Cart addItemToCart(Long productId, int quantity) {
        log.info("CartItemServiceImpl::addItemToCart, productId: {}, quantity: {}", productId, quantity);
        Cart cart = getCart();

        CartItem cartItem = cart
                .getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());

        setResultToCartItem(productId, quantity, cartItem, cart);

        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        return cartService.saveCart(cart);

    }

    @Override
    public Optional<Cart> removeItemFromCart(Long itemId) {
        log.info("CartItemServiceImpl::removeItemFromCart , itemId: {}", itemId);

        Cart cart = cartService.getCart();
        CartItem itemToRemove = getCartItem(cart, itemId);
        cart.removeItem(itemToRemove);
        cartItemRepository.delete(itemToRemove);

        if (cart.getItems().isEmpty()) {
            cartService.deleteCart();
            return Optional.empty();
        }

        return Optional.of(cartService.saveCart(cart));
    }

    @Override
    public Optional<Cart> updateItemQuantity(Long itemId, int quantity) {
        if (quantity == 0) {
            return removeItemFromCart(itemId);
        }

        Cart cart = cartService.getCart();
        CartItem cartItem = getCartItem(cart, itemId);
        cart.updateItem(cartItem, quantity);
        return Optional.of(cartService.saveCart(cart));
    }


    private CartItem getCartItem(Cart cart, Long itemId) {
        return cart.getItems()
                .stream()
                .filter(item -> item.getItemId().equals(itemId))
                .findFirst()
                .orElseThrow(
                        () -> new CustomNotFoundException("Item not found in the cart, itemId: " + itemId)
                );
    }

    private Cart getCart() {
        log.info("CartItemServiceImpl::getCart");
        Long foundCartId = cartService.getCartId();
        if (foundCartId == null) {
            return cartService.initializeNewCart();
        }

        Cart cart = cartService.getCart();
        log.info("CartItemServiceImpl::getCart, Cart found: {}", cart);
        return cart;
    }

    private void setResultToCartItem(Long productId, int quantity, CartItem cartItem, Cart cart) {
        log.info("CartItemServiceImpl::setResultToCartItem, productId: {}, quantity: {}, cartItem: {}", productId, quantity, cartItem);
        if (cartItem.getItemId() == null) {
            Product product = productService.getProductById(productId);

            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
            cartItem.setCategory(product.getCategory().getName());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
    }

}
